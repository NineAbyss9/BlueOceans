
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.IBONeutralMob;
import com.bilibili.player_ix.blue_oceans.api.mob.IBOMob;
import com.bilibili.player_ix.blue_oceans.api.mob.Profession;
import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.AvoidTargetGoal;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.plum.EchoPotion;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.github.NineAbyss9.ix_api.api.mobs.ApiVillager;
import com.github.NineAbyss9.ix_api.api.mobs.ai.goal.ApiTradeWithPlayerGoal;
import com.github.NineAbyss9.ix_api.api.mobs.ai.goal.ConditionalMeleeAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.NineAbyss9.math.MathSupport;

import java.util.*;
import java.util.function.Predicate;
import java.util.random.RandomGenerator;

public class VillagerBiologist
extends BaseVillager
implements RangedAttackMob, IBOMob, IBONeutralMob, ApiVillager {
    private static final UUID SPEED_MODIFIER_DRINKING_UUID = UUID.fromString(
            "5CD17E52-A79A-43D3-A529-90BDE04B181A");
    private static final AttributeModifier SPEED_MODIFIER_DRINKING = new AttributeModifier(
            SPEED_MODIFIER_DRINKING_UUID, "Drinking speed penalty", -0.5,
            AttributeModifier.Operation.ADDITION);
    private static final EntityDataAccessor<Boolean> DATA_USING_ITEM;
    private int usingTime;
    @Nullable
    private Player lastHurtByPlayer;
    private NearestHealableFriendTargetGoal<AbstractVillager> healFriendsGoal;
    private NearestHealableFriendTargetGoal<Player> healPlayersGoal;
    private NearestHealableFriendTargetGoal<AbstractHuntingVillager> healVillagersGoal;
    private AttackTargetGoal<LivingEntity> attackTargetsGoal;
    public VillagerBiologist(EntityType<? extends VillagerBiologist> p_35267_, Level p_35268_) {
        super(p_35267_, p_35268_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_USING_ITEM, false);
    }

    public void registerBehaviors() {
    }

    protected void registerGoals() {
        this.healFriendsGoal = new NearestHealableFriendTargetGoal<>(this, AbstractVillager.class, true);
        this.healVillagersGoal = new NearestHealableFriendTargetGoal<>(this, AbstractHuntingVillager.class,
                true);
        this.healPlayersGoal = new NearestHealableFriendTargetGoal<>(this, Player.class, true,
                player -> this.lastHurtByPlayer != player);
        this.attackTargetsGoal = new AttackTargetGoal<>(this, LivingEntity.class);
        this.goalSelector.addGoal(1, new ClearPlumBlocksGoal(this));
        this.goalSelector.addGoal(1, new CloseAttackGoal(this).canUse(VillagerBiologist::canAttackTarget)
                .better(20));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidTargetGoal(this, 9f, 1.0, 1.2));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 0.8,
                60, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new ApiTradeWithPlayerGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractVillager.class,
                AbstractHuntingVillager.class));
        this.targetSelector.addGoal(1, this.attackTargetsGoal);
        this.targetSelector.addGoal(2, this.healFriendsGoal);
        this.targetSelector.addGoal(3, this.healPlayersGoal);
    }

    public void performRangedAttack(LivingEntity livingEntity, float v) {
        Vec3 delta = livingEntity.getDeltaMovement();
        double $$3 = livingEntity.getX() + delta.x - this.getX();
        double $$4 = livingEntity.getEyeY() - 1.100000023841858 - this.getY();
        double $$5 = livingEntity.getZ() + delta.z - this.getZ();
        double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
        Potion $$7 = this.getPotionWillThrow(livingEntity, $$6);
        ThrownPotion $$8 = new ThrownPotion(this.level(), this);
        $$8.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), $$7));
        $$8.setXRot($$8.getXRot() -20.0F);
        $$8.shoot($$3, $$4 + $$6 * 0.2, $$5, 0.75F, 8.0F);
        if (!this.isSilent()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW,
                    this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
        }
        this.level().addFreshEntity($$8);
    }

    public void throwEchoPotion(BlockPos pPos) {
        double $$3 = pPos.getX() - this.getX();
        double $$4 = pPos.getY() - 1.100000023841858 - this.getY();
        double $$5 = pPos.getZ() - this.getZ();
        double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
        EchoPotion echoPotion = new EchoPotion(this, this.level());
        echoPotion.setXRot(echoPotion.getXRot() - 20.0F);
        echoPotion.shoot($$3, $$4 + $$6 * 0.2, $$5, 0.75F, 8.0F);
        if (!this.isSilent())
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW,
                    this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
        this.level().addFreshEntity(echoPotion);
    }

    @Nullable
    public VillagerTrades.ItemListing[] getTradeLists() {
        return BoVillagerTrades.BIOLOGIST_TRADES;
    }

    public boolean isClearingPlums() {
        return this.hasTask(Task.CLEAR_PLUMS);
    }

    public void setUsingItem(boolean pUsing) {
        this.getEntityData().set(DATA_USING_ITEM, pUsing);
    }

    public boolean isDrinkingPotion() {
        return this.getEntityData().get(DATA_USING_ITEM);
    }

    @SuppressWarnings("deprecation")
    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive()) {
            this.healFriendsGoal.decrementCooldown();
            this.healVillagersGoal.decrementCooldown();
            this.healPlayersGoal.decrementCooldown();
            this.attackTargetsGoal.setCanAttack(this.healFriendsGoal.getCooldown() <= 0 &&
                    this.healPlayersGoal.getCooldown() <= 0 && this.healVillagersGoal.getCooldown()<=0);
            if (this.isDrinkingPotion()) {
                if (this.usingTime-- <= 0) {
                    this.setUsingItem(false);
                    ItemStack $$0 = this.getOffhandItem();
                    this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    if ($$0.is(Items.POTION)) {
                        List<MobEffectInstance> $$1 = PotionUtils.getMobEffects($$0);
                        for (MobEffectInstance $$2 : $$1) {
                            this.addEffect(new MobEffectInstance($$2));
                        }
                    }
                    Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(
                            SPEED_MODIFIER_DRINKING);
                }
            } else {
                Potion $$3 = null;
                if (this.random.nextFloat() < 0.15F && this.isEyeInFluid(FluidTags.WATER) && !this.hasEffect(MobEffects
                        .WATER_BREATHING)) {
                    $$3 = Potions.WATER_BREATHING;
                } else if (this.random.nextFloat() < 0.15F && (this.isOnFire() || this.getLastDamageSource() != null &&
                        this.getLastDamageSource().is(DamageTypeTags.IS_FIRE)) && !this.hasEffect(MobEffects
                        .FIRE_RESISTANCE)) {
                    $$3 = Potions.FIRE_RESISTANCE;
                } else if (this.random.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
                    $$3 = Potions.STRONG_HEALING;
                } else if (this.random.nextFloat() < 0.5F && this.getTarget() != null && !this.hasEffect(MobEffects
                        .MOVEMENT_SPEED) && this.getTarget().distanceToSqr(this) > 121.0) {
                    $$3 = Potions.STRONG_SWIFTNESS;
                }
                if ($$3 != null) {
                    this.setOffHandItem(PotionUtils.setPotion(new ItemStack(Items.POTION), $$3));
                    this.usingTime = this.getOffhandItem().getUseDuration();
                    this.setUsingItem(true);
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_DRINK,
                                this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                    }
                    AttributeInstance $$4 = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    if ($$4 != null) {
                        $$4.removeModifier(SPEED_MODIFIER_DRINKING);
                        $$4.addTransientModifier(SPEED_MODIFIER_DRINKING);
                    }
                }
            }
            if (this.random.nextFloat() < 7.5E-4F) {
                this.level().broadcastEntityEvent(this, (byte)15);
            }
        }
        super.aiStep();
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 15) {
            for(int $$1 = 0; $$1 < this.random.nextInt(35) + 10; ++$$1) {
                this.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, this.getX() + this.random.nextGaussian()
                        * 0.12999999523162842, this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian()
                        * 0.12999999523162842, this.getZ() + this.random.nextGaussian() * 0.12999999523162842,
                        0.0, 0.0, 0.0);
            }
        } else
            super.handleEntityEvent(pId);
    }

    public boolean canBeAffected(MobEffectInstance p_21197_) {
        return p_21197_.getEffect().isBeneficial() &&
                !BlueOceansMobEffects.PLUM_INFECTION.get().equals(p_21197_.getEffect()) &&
                !BlueOceansMobEffects.PLUM_INVADE.get().equals(p_21197_.getEffect()) &&
                !MobEffects.HARM.equals(p_21197_.getEffect());
    }

    protected ItemStack getAttackItem() {
        if (this.canAttackTarget())
            return new ItemStack(Items.IRON_AXE);
        if (getTarget() == null)
            return ItemStack.EMPTY;
        LivingEntity target = getTarget();
        return PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION),
                this.getPotionWillThrow(target, this.getMovement(target)));
    }

    protected ItemStack getDailyItem() {
        return ItemStack.EMPTY;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.VILLAGER_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        Entity entity = p_21016_.getEntity();
        if (entity instanceof Player player) {
            this.lastHurtByPlayer = player;
        }
        return super.hurt(p_21016_, p_21017_);
    }

    public boolean showProgressBar() {
        return false;
    }

    public int getVillagerXp() {
        return 1;
    }

    @Deprecated(since = "1.1.1a")
    @Nullable
    public VillagerBiologist getBreedOffspring(ServerLevel serverLevel, @SuppressWarnings("unused") AgeableMob ageableMob) {
        return new VillagerBiologist(BlueOceansEntities.VILLAGER_BIOLOGIST.get(), serverLevel);
    }

    public double getMovement(LivingEntity livingEntity) {
        Vec3 delta = livingEntity.getDeltaMovement();
        double $$3 = livingEntity.getX() + delta.x - this.getX();
        //double $$4 = livingEntity.getEyeY() - 1.100000023841858 - this.getY();
        double $$5 = livingEntity.getZ() + delta.z - this.getZ();
        return Math.sqrt($$3 * $$3 + $$5 * $$5);
    }

    public Potion getPotionWillThrow(LivingEntity livingEntity, double $$6) {
        Potion $$7 = Potions.STRONG_HARMING;
        if (livingEntity instanceof AbstractVillager) {
            if (livingEntity.getHealth() <= 4.0F) {
                $$7 = Potions.STRONG_HEALING;
            } else {
                $$7 = Potions.STRONG_REGENERATION;
            }
            this.setTarget(null);
        } else if (livingEntity instanceof Player player && this.lastHurtByPlayer != player) {
            if (livingEntity.getHealth() <= 4.0F) {
                $$7 = Potions.STRONG_HEALING;
            } else {
                $$7 = Potions.STRONG_REGENERATION;
            }
            this.setTarget(null);
        } else if ($$6 >= 8.0 && !livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            $$7 = Potions.STRONG_SLOWNESS;
        } else if (livingEntity.getHealth() >= 8.0F && livingEntity.getMobType() != MobType.UNDEAD
                && !livingEntity.hasEffect(MobEffects.POISON)
                && livingEntity.canBeAffected(new MobEffectInstance(MobEffects.POISON))) {
            $$7 = Potions.STRONG_POISON;
        } else if ($$6 <= 3.0 && !livingEntity.hasEffect(MobEffects.WEAKNESS) && this.random.nextFloat() < 0.25F) {
            $$7 = Potions.WEAKNESS;
        } else if (livingEntity.getMobType().equals(MobType.UNDEAD)) {
            $$7 = Potions.STRONG_HEALING;
        }
        return $$7;
    }

    public boolean canAttackTarget() {
        if (this.getTarget() == null)
            return false;
        return this.closerThan(getTarget(), 3);
    }

    public Profession getProfession() {
        return Profession.BIOLOGIST;
    }

    public ApiPose getArmPose() {
        if (this.isAggressive()) {
            if (this.canAttackTarget())
                return ApiPose.ATTACKING;
        }
        return ApiPose.CROSSED;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes
                        .ATTACK_DAMAGE, 2)
                .add(Attributes.MAX_HEALTH, 30).add(Attributes.ARMOR, 2).add(Attributes
                                .FOLLOW_RANGE, 42);
    }

    static {
        DATA_USING_ITEM = SynchedEntityData.defineId(VillagerBiologist.class, EntityDataSerializers.BOOLEAN);
    }

    private static class NearestHealableFriendTargetGoal<T extends LivingEntity>
            extends NearestAttackableTargetGoal<T> {
        private int cooldown = 0;

        public NearestHealableFriendTargetGoal(VillagerBiologist biologist, Class<T> p_26088_, boolean p_26089_,
                                               @Nullable Predicate<LivingEntity> p_26090_) {
            super(biologist, p_26088_, 500, p_26089_, false, p_26090_);
        }

        public NearestHealableFriendTargetGoal(VillagerBiologist biologist, Class<T> clazz, boolean bool) {
            this(biologist, clazz, bool, null);
        }

        public int getCooldown() {
            return this.cooldown;
        }

        public void decrementCooldown() {
            --this.cooldown;
        }

        public boolean canUse() {
            if (this.cooldown <= 0 && this.mob.getRandom().nextBoolean()) {
                this.findTarget();
                return this.target != null;
            } else {
                return false;
            }
        }

        public void start() {
            this.cooldown = reducedTickDelay(400);
            super.start();
        }
    }

    private static class AttackTargetGoal<T extends LivingEntity>
            extends NearestAttackableTargetGoal<T> {
        private boolean canAttack = true;

        AttackTargetGoal(VillagerBiologist biologist, Class<T> tClass) {
            super(biologist, tClass, 10, true, false, living ->
                    living instanceof Enemy && living.isAlive());
        }

        public void setCanAttack(boolean p_26084_) {
            this.canAttack = p_26084_;
        }

        public boolean canUse() {
            return this.canAttack && super.canUse();
        }
    }

    private static class ClearPlumBlocksGoal extends Goal {
        final VillagerBiologist biologist;
        BlockPos pos = BlockPos.ZERO;
        int cooldown;
        public ClearPlumBlocksGoal(VillagerBiologist pMob) {
            this.biologist = pMob;
        }

        public boolean canUse() {
            return findPlumBlock() || findPlumEntity();
        }

        public boolean canContinueToUse() {
            return (!this.pos.equals(BlockPos.ZERO) &&
                    this.biologist.level().getBlockState(pos).is(BoTags.RED_PLUM_BLOCKS)) ||
                    this.findPlumEntity();
        }

        public void start() {
            this.biologist.navigation.moveTo(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 0.8);
        }

        public void tick() {
            if (this.cooldown <= 0) {
                this.biologist.throwEchoPotion(this.pos);
                this.cooldown = 50;
            } else
                --this.cooldown;
        }

        public void stop() {
            this.pos = BlockPos.ZERO;
            this.cooldown = 0;
        }

        @Nullable
        public BlockPos findPos() {
            for (int i = 0;i < 10;i++) {
                RandomGenerator generator = MathSupport.random;
                BlockPos cache = this.biologist.blockPosition().offset(generator.nextInt(20) - 10,
                        2 - generator.nextInt(5), generator.nextInt(20) - 10);
                if (this.biologist.level().getBlockState(cache).is(BoTags.RED_PLUM_BLOCKS))
                    return cache;
            }
            return BlockPos.ZERO;
        }

        public boolean findPlumBlock() {
            return !BlockPos.ZERO.equals((this.pos = findPos()));
        }

        public boolean findPlumEntity() {
            if (this.biologist.getTarget() instanceof RedPlumMob mob) {
                this.pos = ((Entity)mob).blockPosition();
                return true;
            } else if (this.biologist.getTarget() == null) {
                for (LivingEntity entity : this.biologist.level().getEntitiesOfClass(LivingEntity.class,
                        this.biologist.getBoundingBox().inflate(16), entity -> entity instanceof RedPlumMob)) {
                    this.biologist.setTarget(entity);
                    this.pos = entity.blockPosition();
                    return true;
                }
            }
            return false;
        }
    }

    private static class CloseAttackGoal extends ConditionalMeleeAttackGoal<VillagerBiologist> {
        public CloseAttackGoal(VillagerBiologist finder) {
            super(finder, 0.8, 2);
        }

        public void start() {
            super.start();
            ((VillagerBiologist)this.convert()).setHandItemToAttack();
        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                this.mob.setAggressive(false);
                ((VillagerBiologist)this.convert()).setHandItemToDaily();
                this.mob.getNavigation().stop();
            } else {
                ((VillagerBiologist)this.convert()).setHandItemToAttack();
            }
        }
    }
}
