
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.api.mob.IBehaviorUser;
import com.bilibili.player_ix.blue_oceans.api.mob.MobTypes;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorSelector;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.MoveToBlockBehavior;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl.AbstractGirl;
import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.*;
import com.bilibili.player_ix.blue_oceans.util.MobUtil;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.player_ix.ix_api.api.ApiPose;
import com.github.player_ix.ix_api.api.mobs.ApiPoseMob;
import com.github.player_ix.ix_api.api.mobs.MobUtils;
import com.github.player_ix.ix_api.api.mobs.OwnableMob;
import com.github.player_ix.ix_api.api.mobs.effect.EffectInstance;
import com.github.player_ix.ix_api.util.ParticleUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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

    public void aiStep() {
        super.aiStep();
        if (this.getInfectLevel() > 2 && this.level().isClientSide) {
            BlueOceansParticleTypes.addRedSpell(this.level(), this.getRandomX(0.8), this.getRandomY(),
                    this.getRandomZ(0.8), 0.2);
        }
        if (this.getFeetBlockState().is(BlueOceansBlocks.RED_PLUM_BLOCK.get())) {
            this.standOnPlumTick();
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

    public void checkAndPlusInfectLevel(LivingEntity living) {
        if (this.shouldUpLevel() && !this.level().isClientSide
                && this.getType() != this.getNextLevelConvert()) {
            ServerLevel serverLevel = this.getServerLevel();
            var entityType = this.getNextLevelConvert();
            AbstractRedPlumMob mob = null;
            if (entityType != null) {
                mob = entityType.create(serverLevel);
            }
            if (mob != null) {
                mob.moveTo(this.position());
                if (serverLevel.addFreshEntity(mob)) {
                    this.discard();
                    return;
                }
            }
        }
        if (this.getRandom().nextFloat() <= this.getPlusLevelChance() || living.getMaxHealth() >= 50) {
            this.setInfectLevelPlus();
        }
    }

    public void standOnPlumTick() {
        if (this.getRandomUtil().nextInt(25) == 0) {
            this.setInfectLevelPlus();
        }
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        List<EntityType<? extends AbstractRedPlumMob>> list = RedPlumUtil.MAP.get(
                this.getLevel() + 1);
        if (list != null) {
            return list.get(this.getRandomUtil().nextInt(list.size()));
        } else {
            return null;
        }
    }

    protected int nextConvertUpNeeds() {
        return RedPlumUtil.PLUM_PLUS_KILLS.intValue(this.getLevel() - 1);
    }

    protected boolean shouldUpLevel() {
        return this.getInfectLevel() >= this.nextConvertUpNeeds();
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

    protected float getPlusLevelChance() {
        return 0.1F;
    }

    protected void addHostileGoal(int t) {
        this.targetSelector.addGoal(t, new RedPlumMobsNearestAttackableTargetGoal(this, false));
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
            return mobs.isException() && super.hurt(pSource, pAmount);
        }
        if (!MobUtil.canHurt(this, entity)) {
            return false;
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
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Kills", this.getKills());
        tag.putInt("InfectLevel", this.getInfectLevel());
        super.addAdditionalSaveData(tag);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Kills")) {
            this.setKills(tag.getInt("Kills"));
        }
        if (tag.contains("InfectLevel")) {
            this.setInfectLevel(tag.getInt("InfectLevel"));
        }
        super.readAdditionalSaveData(tag);
    }

    public void doKillEntity(LivingEntity pEntity) {
        this.checkAndPlusInfectLevel(pEntity);
        this.spawnBreedMob(pEntity);
    }

    public void spawnBreedMob(LivingEntity pEntity) {
        if (!this.level().isClientSide) {
            ParticleUtil.sendParticles(this.getServerLevel(), BlueOceansParticleTypes.RED_PLUM_SPELL.get(),
                    pEntity.position(), 12, 0.7, 0.7, 0.7, 0);
            NeoPlum neoPlum = BlueOceansEntities.NEO_PLUM.get().create(this.getServerLevel());
            if (neoPlum != null) {
                neoPlum.moveTo(pEntity.position());
                this.getServerLevel().addFreshEntity(neoPlum);
            }
        }
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

    protected void doAttackTarget(Entity pEntity) {
        if (pEntity instanceof LivingEntity entity) {
            entity.addEffect(EffectInstance.create(BlueOceansMobEffects.PLUM_INVADE,
                    300, RedPlumUtil.plumInvadeLevel));
        }
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
        if (pEntity.getMaxHealth() < 30) {
            this.setKills(this.getKills() + 1);
        } else {
            this.setKills(this.getKills() + 3);
        }
        this.doKillEntity(pEntity);
        this.heal(this.getHealAmount());
        return super.killedEntity(pLevel, pEntity);
    }

    protected static class RedPlumMobsNearestAttackableTargetGoal
            extends NearestAttackableTargetGoal<LivingEntity> {
        protected final AbstractRedPlumMob mobs;

        public RedPlumMobsNearestAttackableTargetGoal(AbstractRedPlumMob p_26060_, boolean p_26062_) {
            super(p_26060_, LivingEntity.class, p_26062_, entity -> {
                try {
                    if (entity.getType().getDescriptionId().equals("noixmodapi.entity.apostle") &&
                            entity.getClass().getMethod("getTitleNumber").getDefaultValue() instanceof Integer i) {
                        return i < 11;
                    }
                } catch (Throwable ignore) {
                }
                return  !(entity instanceof RedPlumMob);
            });
            this.mobs = p_26060_;
        }

        public boolean canUse() {
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
        }

        public void start() {
            super.start();
            if (this.targetMob != null) {
                this.alertOther(this.mob, this.targetMob);
            }
        }

        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            }
            return !(this.targetMob instanceof AbstractRedPlumMob);
        }
    }

    protected static class MoveToPlumBehavior extends MoveToBlockBehavior {
        public MoveToPlumBehavior(PathfinderMob pMob, double pSpeed) {
            super(pMob, pSpeed, 20, BoTags.RED_PLUM_BLOCKS);
        }

        public boolean canUse() {
            if (this.mob.getTarget() != null) {
                return false;
            }
            return super.canUse();
        }

        public boolean canContinueToUse() {
            if (this.mob.getTarget() != null) {
                return false;
            }
            return super.canContinueToUse();
        }
    }
}
