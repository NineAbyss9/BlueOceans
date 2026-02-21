
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.api.mob.BoMobType;
import com.bilibili.player_ix.blue_oceans.api.mob.IBehaviorUser;
import com.bilibili.player_ix.blue_oceans.api.mob.MobTypes;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorSelector;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.MoveToBlockBehavior;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl.AbstractGirl;
import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.compat.noixapi.NoIXApiCompat;
import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import com.bilibili.player_ix.blue_oceans.init.*;
import com.bilibili.player_ix.blue_oceans.util.MobUtil;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.github.NineAbyss9.ix_api.api.mobs.ApiEntityDataSerializers;
import com.github.NineAbyss9.ix_api.api.mobs.ApiPoseMob;
import com.github.NineAbyss9.ix_api.api.mobs.MobUtils;
import com.github.NineAbyss9.ix_api.api.mobs.OwnableMob;
import com.github.NineAbyss9.ix_api.api.mobs.effect.EffectInstance;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import com.github.NineAbyss9.ix_api.util.Vec9;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.util.Action;

import javax.annotation.Nullable;
import java.util.List;

/**Base Plum Class*/
public abstract class AbstractRedPlumMob
extends OwnableMob
implements RedPlumMob, ApiPoseMob, IBehaviorUser {
    protected static final EntityDataAccessor<Integer> DATA_KILLS =
            SynchedEntityData.defineId(AbstractRedPlumMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_INFECT_LEVEL =
            SynchedEntityData.defineId(AbstractRedPlumMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DATA_IS_CONTROLLED_BY_GIRL =
            SynchedEntityData.defineId(AbstractRedPlumMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Vec9> DATA_TARGET_POS;
    protected static final EntityDataAccessor<Integer> DATA_PLUM_FLAGS;
    protected final BehaviorSelector behaviorSelector;
    protected boolean isException;
    protected AbstractRedPlumMob(EntityType<? extends AbstractRedPlumMob> type, Level level) {
        super(type, level);
        this.behaviorSelector = new BehaviorSelector(level.getProfilerSupplier());
        this.registerBehaviors();
    }

    public void registerBehaviors() {
        this.addMoveToPlumBehavior(4, 1.0);
    }

    public void tick() {
        super.tick();
        if (this.isMovingToBuilder()) {
            this.moveToBuilderTick();
        }
    }

    public void aiStep() {
        super.aiStep();
        if (this.getFeetBlockState().is(BoTags.PLUMS_CAN_UPGRADE)) {
            this.standOnPlumTick();
        }
    }

    protected void clientAiStep() {
        if (this.shouldLevelUp()) {
            BlueOceansParticleTypes.addRedSpell(this.level(), this.getRandomX(0.8), this.getRandomY(),
                    this.getRandomZ(0.8), 0.2);
        }
    }

    protected void customServerAiStep() {
        this.behaviorTick();
        super.customServerAiStep();
    }

    /**Implements {@linkplain IBehaviorUser#getBehaviorSelector()} method*/
    public BehaviorSelector getBehaviorSelector() {
        return behaviorSelector;
    }

    public int getKills() {
        return this.entityData.get(DATA_KILLS);
    }

    protected void setKills(int pKills) {
        this.entityData.set(DATA_KILLS, pKills);
    }

    public void setKillsPlus() {
        this.setKills(this.getKills() + 1);
    }

    public int getInfectLevel() {
        return this.entityData.get(DATA_INFECT_LEVEL);
    }

    public void checkAndPlusInfectLevel(LivingEntity pEntity) {
        if (this.tryConvert(pEntity))
            return;
        if (this.getRandomUtil().nextFloat() <= this.getUpgradeChance() || pEntity.getMaxHealth() >= 20.0F) {
            this.setInfectLevelPlus();
        }
    }

    public boolean tryConvert(LivingEntity pEntity) {
        if (!this.level().isClientSide && this.shouldLevelUp() && this.getType() != this.getNextLevelConvert()) {
            ServerLevel serverLevel = this.serverLevel();
            var entityType = this.getNextLevelConvert();
            AbstractRedPlumMob mob = null;
            if (entityType != null) {
                mob = entityType.create(serverLevel);
            }
            if (mob != null) {
                mob.moveTo(this.position());
                if (serverLevel.addFreshEntity(mob)) {
                    this.discard();
                    return true;
                }
            }
        }
        return false;
    }

    public void standOnPlumTick() {
        if (this.tickCount % 20 == 0 && this.getRandomUtil().nextFloat() < 0.01F) {
            this.setInfectLevelPlus();
        }
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        if (this.getLevel() == 1)
            return BlueOceansEntities.RED_DEMON.get();
        List<EntityType<? extends AbstractRedPlumMob>> list = RedPlumUtil.MAP.get(
                this.getLevel() + 1);
        if (list != null) {
            return list.get(this.getRandomUtil().nextInt(list.size()));
        }
        return null;
    }

    protected int nextConvertUpNeeds() {
        return RedPlumUtil.PLUM_PLUS_KILLS.intValue(this.getLevel() - 1);
    }

    protected boolean shouldLevelUp() {
        return this.getInfectLevel() >= this.nextConvertUpNeeds();
    }

    public float spawnFighterChance() {
        return 0.1F;
    }

    protected void setInfectLevel(int pLevel) {
        this.entityData.set(DATA_INFECT_LEVEL, pLevel);
    }

    public void setInfectLevelPlus() {
        this.setInfectLevel(this.getInfectLevel() + 1);
    }

    public float getHealAmount() {
        return 1 + this.getInfectLevel();
    }

    public boolean isException() {
        return this.isException;
    }

    protected float getUpgradeChance() {
        return 0.4F;
    }

    public MobType getMobType() {
        return BoMobType.RED_PLUM;
    }

    protected void addHostileGoal(int t) {
        this.targetSelector.addGoal(t, new RedPlumMobsNearestAttackableTargetGoal(this, true));
    }

    protected void addFriendlyGoal(int $int, boolean $boolean) {
        this.targetSelector.addGoal($int, new NearestAttackableTargetGoal<>(this, LivingEntity.class, $boolean,
                living -> living instanceof Enemy));
    }

    protected void addMoveToPlumBehavior(int p, double pSpeed) {
        this.behaviorSelector.addBehavior(p, new MoveToPlumBehavior(this, pSpeed));
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity entity = pSource.getEntity();
        if (entity instanceof RedPlumMob mobs) {
            return (mobs.isException() || this.isException()) && super.hurt(pSource, pAmount);
        }
        if (!MobUtil.canHurt(this, entity)) {
            return false;
        }
        if (pSource.is(DamageTypeTags.IS_FIRE))
            pAmount /= 2.0F;
        if (this.getKills() > 4 //&& (!(entity instanceof LivingEntity living) ||
                //living.getItemInHand(InteractionHand.MAIN_HAND).getEnchantmentLevel())
        ) {
            pAmount /= 2.0F;
        }
        if (entity instanceof LivingEntity living) {
            if (living.getMainHandItem().isEmpty())
                living.addEffect(EffectInstance.create(BlueOceansMobEffects.PLUM_INFECTION,
                        170));
        }
        return super.hurt(pSource, pAmount);
    }

    public boolean canAttack(LivingEntity pTarget) {
        if (pTarget instanceof RedPlumMob && !this.isException()) {
            return false;
        }
        if (pTarget instanceof AbstractGirl) {
            return false;
        }
        if (this.getOwner() instanceof Player && !this.isHostile()) {
            return false;
        }
        return super.canAttack(pTarget);
    }

    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        MobEffect mobEffect = pEffectInstance.getEffect();
        if (mobEffect.equals(MobEffects.POISON)) {
            return false;
        }
        return super.canBeAffected(pEffectInstance);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_KILLS, 0);
        this.entityData.define(DATA_INFECT_LEVEL, 1);
        this.entityData.define(DATA_IS_CONTROLLED_BY_GIRL, false);
        this.entityData.define(DATA_TARGET_POS, Vec9.of());
        this.entityData.define(DATA_PLUM_FLAGS, 0);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Kills", this.getKills());
        tag.putInt("InfectLevel", this.getInfectLevel());
        tag.putInt("PlumFlag", this.getPlumFlag());
        super.addAdditionalSaveData(tag);
        Vec9.createVec9Tag(tag, this.getTargetPos(), "TargetPos");
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Kills")) {
            this.setKills(tag.getInt("Kills"));
        }
        if (tag.contains("InfectLevel")) {
            this.setInfectLevel(tag.getInt("InfectLevel"));
        }
        this.setPlumFlag(tag.getInt("PlumFlag"));
        super.readAdditionalSaveData(tag);
        this.setTargetPos(Vec9.readVec3Tag(tag, "TargetPos"));
    }

    public boolean isMovingToBuilder() {
        return this.isPlumFlag(1);
    }

    public void moveToBuilderTick() {
        Vec9 vec9 = this.getTargetPos();
        this.getNavigation().moveTo(vec9.x, vec9.y, vec9.z, 0.8);
        if (this.closerThan(this.getTargetPos(), 2)) {
            new Action(() -> ((PlumBuilder)this.getOwner()).anotherJoin(this), () -> {
                this.setOwner(null);
                this.setTargetPos(null);
                this.setPlumFlag(0);
            }).run(this.getOwner() instanceof PlumBuilder);
        }
    }

    public boolean copyTo(AbstractRedPlumMob pOther) {
        pOther.moveTo(position());
        pOther.setOwner(this.getOwner());
        return true;
    }

    public void die(DamageSource pDamageSource) {
        if (!this.level().isClientSide) {
            BlockPos currentPos = blockPosition();
            BlockState state = this.level().getBlockState(currentPos);
            BlockState neoPlum = ((MultifaceBlock)BlueOceansBlocks.BUDDING_NEO_PLUM.get())
                    .getStateForPlacement(state, level(), currentPos, Direction.DOWN);
            if (neoPlum != null) {
                Action.emptyTrue(() -> this.level().setBlockAndUpdate(currentPos, neoPlum))
                        .run(state.is(BoTags.RED_PLUM_BLOCKS) || !state.canBeReplaced()
                                || fallDistance > 0.25F);
            }
        }
        super.die(pDamageSource);
    }

    public void doKillEntity(LivingEntity pEntity) {
        if (pEntity.getMaxHealth() < 20.0F) {
            this.setKillsPlus();
        } else {
            this.setKills(this.getKills() + 3);
        }
        this.checkAndPlusInfectLevel(pEntity);
        this.spawnBreedMob(pEntity);
        Action.emptyFalse(() -> level().setBlockAndUpdate(pEntity.blockPosition(),
                BlueOceansBlocks.PLUM_CELL_CLUSTER.get().defaultBlockState()))
                .run(!level().getBlockState(pEntity.blockPosition()).is(BoTags.RED_PLUM_BLOCKS));
    }

    public void spawnBreedMob(LivingEntity pEntity) {
        if (!this.level().isClientSide) {
            if (RedPlumUtil.likeHuman(pEntity))
                RedPlumUtil.spawnRedPlumHuman(this.level(), pEntity);
            else if (RedPlumUtil.likeVillager(pEntity))
                RedPlumUtil.spawnRedPlumVillager(this.level(), pEntity);
            else {
                AbstractRedPlumMob entity = this.random.nextFloat() <= this.spawnFighterChance() ?
                        BlueOceansEntities.NEO_FIGHTER.get().create(this.serverLevel()) :
                        BlueOceansEntities.NEO_PLUM.get().create(this.serverLevel());
                ParticleUtil.sendParticles(this.serverLevel(), BlueOceansParticleTypes.RED_PLUM_SPELL.get(),
                        pEntity.position(), 12, 0.7, 0.7, 0.7, 0);
                if (entity != null) {
                    entity.moveTo(pEntity.position());
                    this.serverLevel().addFreshEntity(entity);
                }
            }
        }
    }

    public boolean shouldAttackOtherMobs() {
        return this.getPlumFlag() != 1;
    }

    public ApiPose getPoses() {
        return ApiPose.NATURAL;
    }

    public boolean isControlledByGirl() {
        return this.entityData.get(DATA_IS_CONTROLLED_BY_GIRL);
    }

    public void setControlledByGirl(boolean flag) {
        this.entityData.set(DATA_IS_CONTROLLED_BY_GIRL, flag);
    }

    public MobTypes getMobTypes() {
        return MobTypes.HOSTILE;
    }

    public boolean isHalfHealth() {
        return MobUtils.isHalfHealth(this);
    }

    public int getPlumFlag() {
        return this.entityData.get(DATA_PLUM_FLAGS);
    }

    public boolean isPlumFlag(int pFlag) {
        return this.getPlumFlag() == pFlag;
    }

    public void setPlumFlag(int pFlag) {
        this.entityData.set(DATA_PLUM_FLAGS, pFlag);
    }

    public Vec9 getTargetPos() {
        return this.entityData.get(DATA_TARGET_POS);
    }

    public boolean isEmptyTarget() {
        return this.getTargetPos().equals(Vec3.ZERO);
    }

    public void setTargetPos(@Nullable Vec3 pPos) {
        if (pPos instanceof Vec9 vec9)
            this.entityData.set(DATA_TARGET_POS, vec9);
        else
            this.entityData.set(DATA_TARGET_POS, pPos == null ? Vec9.of() : Vec9.of(pPos));
    }

    protected void doAttackTarget(Entity pEntity) {
        if (pEntity instanceof LivingEntity entity) {
            entity.addEffect(EffectInstance.create(BlueOceansMobEffects.PLUM_INVADE, 300,
                    this.getPlumInvadeLevel()));
        }
    }

    protected int getPlumInvadeLevel() {
        return BoCommonConfig.PLUM_INVADE_LEVEL.get();
    }

    protected void doAttackTargetAlways(Entity pEntity) {
    }

    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = super.doHurtTarget(pEntity);
        if (flag) {
            this.doAttackTarget(pEntity);
        }
        this.doAttackTargetAlways(pEntity);
        return flag;
    }

    public boolean killedEntity(ServerLevel pLevel, LivingEntity pEntity) {
        this.doKillEntity(pEntity);
        this.heal(this.getHealAmount());
        return super.killedEntity(pLevel, pEntity);
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource pSource) {
        return SoundEvents.SCULK_BLOCK_BREAK;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.SCULK_CATALYST_BREAK;
    }

    static {
        DATA_TARGET_POS = SynchedEntityData.defineId(AbstractRedPlumMob.class,
                ApiEntityDataSerializers.VEC9);
        DATA_PLUM_FLAGS = SynchedEntityData.defineId(AbstractRedPlumMob.class,
                EntityDataSerializers.INT);
    }

    protected static class RedPlumMobsNearestAttackableTargetGoal
            extends NearestAttackableTargetGoal<LivingEntity> {
        protected final AbstractRedPlumMob mobs;

        public RedPlumMobsNearestAttackableTargetGoal(AbstractRedPlumMob pMob, boolean mustSee) {
            super(pMob, LivingEntity.class, mustSee, entity -> {
                if (!NoIXApiCompat.isApiLoaded())
                    return !(entity instanceof RedPlumMob);
                if (entity.getType().getDescriptionId().equals("entity.noixmodapi.apostle")) {
                    return false;
                }
                return !(entity instanceof RedPlumMob);
            });
            this.mobs = pMob;
        }

        public boolean canUse() {
            if (this.mobs.isMovingToBuilder())
                return false;
            return this.mobs.getMobTypes().isHostile() && super.canUse();
        }

        public boolean canContinueToUse() {
            if (!this.mobs.getMobTypes().isHostile()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    protected static class RedPlumsMobsHurtByTargetGoal extends HurtByTargetGoal {
        public RedPlumsMobsHurtByTargetGoal(PathfinderMob pMob, Class<?>... classes) {
            super(pMob, classes);
            this.setAlertOthers(AbstractRedPlumMob.class);
        }
    }

    protected static class MoveToPlumBehavior extends MoveToBlockBehavior {
        public MoveToPlumBehavior(PathfinderMob pMob, double pSpeed) {
            super(pMob, pSpeed, 20, BoTags.PLUMS_CAN_UPGRADE);
        }

        public boolean canUse() {
            if (this.mob.getTarget() != null) {
                return false;
            }
            if (((AbstractRedPlumMob)this.convert()).isMovingToBuilder())
                return false;
            return super.canUse();
        }

        public boolean canContinueToUse() {
            if (this.mob.getTarget() != null) {
                return false;
            }
            if (((AbstractRedPlumMob)this.convert()).isMovingToBuilder())
                return false;
            return super.canContinueToUse();
        }
    }
}
