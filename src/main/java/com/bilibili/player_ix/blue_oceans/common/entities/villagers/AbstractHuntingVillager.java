
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.IBehaviorUser;
import com.bilibili.player_ix.blue_oceans.api.mob.Profession;
import com.bilibili.player_ix.blue_oceans.common.entities.AbstractBlueOceansMob;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.Behavior;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorFlag;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorSelector;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.github.NineAbyss9.ix_api.api.mobs.ApiVillager;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractHuntingVillager
extends AbstractBlueOceansMob
implements RangedAttackMob, NeutralMob, ApiVillager, IBehaviorUser, InventoryCarrier {
    @Nullable
    protected Player tradingPlayer;
    private MerchantOffers offers = new MerchantOffers();
    public static final Map<Item, Integer> FOOD_POINTS;
    protected static final Set<Item> WANTED_ITEMS;
    private final SimpleContainer simpleContainer = new SimpleContainer(64);
    public final BehaviorSelector behaviorSelector;
    protected int remainingPersistentAngerTime = 10;
    protected static final EntityDataAccessor<Optional<UUID>> DATA_TARGET_UUID;
    protected static final EntityDataAccessor<Integer> DATA_TARGET_ID;
    protected AbstractHuntingVillager(EntityType<? extends AbstractHuntingVillager> pType, Level level) {
        super(pType, level);
        this.setHandItemToDaily();
        this.behaviorSelector = new BehaviorSelector(level.getProfilerSupplier());
        this.registerBehaviors();
        this.restockAll();
        this.updateTrades();
        this.setCanPickUpLoot(true);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TARGET_UUID, Optional.empty());
        this.entityData.define(DATA_TARGET_ID, -1);
    }

    public BehaviorSelector getBehaviorSelector() {
        return behaviorSelector;
    }

    public void registerBehaviors() {
    }

    public void makeParticleAroundSelf() {
        if (this.isEffectiveAi()) {
            this.serverLevel().sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + 1,
                    this.getZ(), 8,  0.5, 0.5, 0.5, 0.1);
        }
    }

    public SimpleContainer getInventory() {
        return simpleContainer;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isAggressive()) {
            return SoundEvents.VILLAGER_NO;
        }
        return SoundEvents.VILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.VILLAGER_HURT;
    }

    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_.getEntity() instanceof AbstractHuntingVillager villager
            && !villager.isAgent())
            return false;
        boolean flag = super.hurt(p_21016_, p_21017_);
        if (flag) {
            this.makeParticleAroundSelf();
            return true;
        }
        return false;
    }

    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        this.stopTrading();
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    public void aiStep() {
        super.aiStep();
        if (this.tradingPlayer != null) {
            lookControl.setLookAt(tradingPlayer);
            navigation.stop();
            if (this.getTarget() != null) {
                stopTrading();
            }
        }
    }

    protected void customServerAiStep() {
        this.behaviorTick();
        super.customServerAiStep();
    }

    @Nullable
    public LivingEntity getTarget() {
        if (this.level().isClientSide)
            return this.level().getEntity(this.getTargetId()) instanceof LivingEntity entity ? entity : null;
        UUID uuid = this.getTargetUUID();
        return uuid == null ? null : serverLevel().getEntity(uuid) instanceof LivingEntity entity ? entity : null;
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        if (pTarget != null) {
            this.setTargetId(pTarget.getId());
            this.setTargetUUID(pTarget.getUUID());
        } else {
            this.setTargetId(-1);
            this.setTargetUUID(null);
        }
    }

    @Nullable
    public UUID getTargetUUID() {
        return this.entityData.get(DATA_TARGET_UUID).orElse(null);
    }

    public void setTargetUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_TARGET_UUID, Optional.ofNullable(uuid));
    }

    public int getTargetId() {
        return this.entityData.get(DATA_TARGET_ID);
    }

    public void setTargetId(int pId) {
        this.entityData.set(DATA_TARGET_ID, pId);
    }

    public void performRangedAttack(LivingEntity livingEntity, float v) {}

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.getTargetUUID();
    }

    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.setTargetUUID(uuid);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(this.remainingPersistentAngerTime);
    }

    public void setLastHurtByMob(@Nullable LivingEntity p_21039_) {
        super.setLastHurtByMob(p_21039_);
    }

    public boolean canAttack(LivingEntity pTarget) {
        if (pTarget instanceof AbstractVillager) {
            return false;
        }
        if (pTarget instanceof AbstractGolem) {
            return false;
        }
        return super.canAttack(pTarget);
    }

    public Set<Item> getWantedItems() {
        return WANTED_ITEMS;
    }

    @Nullable
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    public void startTrading(Player player) {
        this.setTradingPlayer(player);
        this.openTradingScreen(player, this.getDisplayName(), 1);
        player.awardStat(Stats.TALKED_TO_VILLAGER);
    }

    public void stopTrading() {
        if (this.tradingPlayer != null) {
            tradingPlayer.closeContainer();
            this.setTradingPlayer(null);
        }
    }

    public MerchantOffers getOffers() {
        if (this.offers == null)
            offers = new MerchantOffers();
        return this.offers;
    }

    @Nullable
    public LivingEntity getLastHurtByMob() {
        return super.getLastHurtByMob();
    }

    public int getVillagerXp() {
        return 1;
    }

    public void notifyTrade(MerchantOffer merchantOffer) {
        merchantOffer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    public void notifyTradeUpdated(ItemStack itemStack) {
        if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.playSound(this.getTradeUpdatedSound(!itemStack.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
        }
    }

    public void updateTrades() {
        VillagerTrades.ItemListing[] listings = this.getTradeLists();
        if (listings != null) {
            this.addOffersFromItemListings(this.getOffers(), listings);
        }
    }

    protected void addOffersFromItemListings(MerchantOffers merchantOffers, VillagerTrades.ItemListing[] listings) {
        addOffersFromItemListings(merchantOffers, listings, this, this.random);
    }

    public static void addOffersFromItemListings(MerchantOffers merchantOffers, VillagerTrades.ItemListing[] listings,
                                                 Entity pTrader, RandomSource source) {
        Set<Integer> set = Sets.newHashSet();
        /*if (listings.length > 4)
            while (set.size() < 4)
                set.add(this.random.nextInt(listings.length));
        else*/
        for (int i = 0; i < listings.length; ++i)
            set.add(i);
        for (int integer : set) {
            VillagerTrades.ItemListing villagertrades$itemlisting = listings[integer];
            MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(pTrader, source);
            if (merchantoffer != null)
                merchantOffers.add(merchantoffer);
        }
    }

    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (this.getTarget() == null && this.getTradeLists() != null && !this.isTrading()) {
            this.startTrading(pPlayer);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Nullable
    public VillagerTrades.ItemListing[] getTradeLists() {
        return null;
    }

    public void overrideXp(int i) {
    }

    public void overrideOffers(MerchantOffers merchantOffers) {
    }

    public boolean showProgressBar() {
        return true;
    }

    @Nullable
    public Entity changeDimension(ServerLevel pDestination) {
        this.stopTrading();
        return super.changeDimension(pDestination);
    }

    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_TRADE;
    }

    public boolean isClientSide() {
        return this.level().isClientSide;
    }

    public abstract boolean isAgent();

    public abstract boolean canAttackEvenBaby();

    public abstract Profession getProfession();

    protected abstract ItemStack getAttackItem();

    protected ItemStack getDailyItem() {
        return this.getAttackItem();
    }

    protected ItemStack getWorkItem() {
        Item item = this.getProfession().requestedItems.peek();
        return item == null ? this.getAttackItem() : item.getDefaultInstance();
    }

    protected void setHandItemToAttack(InteractionHand hand) {
        this.setItemInHand(hand, this.getAttackItem());
    }

    protected void setHandItemToAttack() {
        this.setHandItemToAttack(InteractionHand.MAIN_HAND);
    }

    protected void setHandItemToDaily(InteractionHand hand) {
        this.setItemInHand(hand, this.getDailyItem());
    }

    protected void setHandItemToDaily() {
        this.setHandItemToDaily(InteractionHand.MAIN_HAND);
    }

    protected void setHandItemToWork(InteractionHand hand) {
        this.setItemInHand(hand, this.getWorkItem());
    }

    protected void setHandItemToWork() {
        this.setHandItemToWork(InteractionHand.MAIN_HAND);
    }

    public ApiPose getArmPose() {
        return ApiPose.CROSSED;
    }

    static {
        FOOD_POINTS = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT,
                1);
        WANTED_ITEMS = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT,
                Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, Items.BEETROOT_SEEDS,
                Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD);
        DATA_TARGET_UUID = SynchedEntityData.defineId(AbstractHuntingVillager.class,
                EntityDataSerializers.OPTIONAL_UUID);
        DATA_TARGET_ID = SynchedEntityData.defineId(AbstractHuntingVillager.class, EntityDataSerializers.INT);
    }

    protected static class VillagerAttackBehavior
    extends Behavior {
        protected AbstractHuntingVillager mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private float ticksUntilNextAttack;
        private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private final boolean canPenalize = false;
        protected final double attackSqr;

        public VillagerAttackBehavior(AbstractHuntingVillager pMob, double pV, boolean pFollow, double attackSqr) {
            this.mob = pMob;
            this.speedModifier = pV;
            this.followingTargetEvenIfNotSeen = pFollow;
            this.attackSqr = attackSqr;
            this.setFlags(EnumSet.of(BehaviorFlag.MOVE, BehaviorFlag.LOOK, BehaviorFlag.JUMP));
        }

        public VillagerAttackBehavior(AbstractHuntingVillager pMob, double pV, boolean pFollow) {
            this(pMob, pV, pFollow, 4);
        }

        public boolean canUse() {
            long i = this.mob.level().getGameTime();
            if (this.mob.isBaby()) {
                return false;
            }
            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                Path path;
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else if (this.canPenalize) {
                    if (--this.ticksUntilNextPathRecalculation <= 0) {
                        path = this.mob.getNavigation().createPath(livingentity, 0);
                        this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                        return path != null;
                    } else {
                        return true;
                    }
                } else {
                    path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (path != null) {
                        return true;
                    } else {
                        return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(),
                                livingentity.getY(), livingentity.getZ());
                    }
                }
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                return false;
            } else if (this.mob.isBaby()) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() &&
                        !((Player)livingentity).isCreative();
            }
        }

        public void start() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                this.mob.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), this.speedModifier);
            }
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            this.mob.setHandItemToAttack();
        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget(null);
            }
            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
            this.mob.setHandItemToDaily();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null) {
                this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
                if (this.mob.getMainHandItem().isEmpty()) {
                    this.mob.setHandItemToAttack();
                }
                double d0 = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) &&
                        this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 &&
                        this.pathedTargetY == 0.0 &&
                        this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX,
                        this.pathedTargetY, this.pathedTargetZ) >= 1.0
                        || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = livingentity.getX();
                    this.pathedTargetY = livingentity.getY();
                    this.pathedTargetZ = livingentity.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                    if (this.canPenalize) {
                        this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
                        if (this.mob.getNavigation().getPath() != null) {
                            Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                            if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x,
                                    finalPathPoint.y, finalPathPoint.z) < 1.0) {
                                this.failedPathFindingPenalty = 0;
                            } else {
                                this.failedPathFindingPenalty += 10;
                            }
                        } else {
                            this.failedPathFindingPenalty += 10;
                        }
                    }
                    if (d0 > 1024.0) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }
                    if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }
                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
                this.checkAndPerformAttack(livingentity, d0);
            }
        }

        protected void checkAndPerformAttack(LivingEntity pTarget, double pRange) {
            double d0 = this.getAttackReachSqr(pTarget);
            if (pRange <= d0 && this.ticksUntilNextAttack <= 0) {
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(pTarget);
            }
        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(20);
        }

        protected double getAttackReachSqr(LivingEntity pTarget) {
            return this.mob.getBbWidth() * this.attackSqr * this.mob.getBbWidth() * this.attackSqr + pTarget.getBbWidth();
        }
    }

    protected static class VillagerHurtByTargetGoal
    extends HurtByTargetGoal {
        public VillagerHurtByTargetGoal(PathfinderMob p_26039_, Class<?>... p_26040_) {
            super(p_26039_, p_26040_);
        }

        public boolean canUse() {
            return super.canUse() && (!(this.targetMob instanceof BaseVillager villager) || villager.isAgent());
        }
    }
}
