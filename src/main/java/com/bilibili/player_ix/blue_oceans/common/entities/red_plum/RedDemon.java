
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.util.MobUtil;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RedDemon
extends RedPlumMonster
implements IAnimatedMob, IFlagMob {
    private static final EntityDataAccessor<Integer> DATA_ANI_TICK;
    public AnimationState idle = new AnimationState();
    public AnimationState attack = new AnimationState();
    public AnimationState summon = new AnimationState();
    public AnimationState explode = new AnimationState();
    public RedDemon(EntityType<? extends RedDemon> type, Level level) {
        super(type, level);
        this.setMaxUpStep(2.0F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANI_TICK, 0);
    }

    protected void registerGoals() {
        this.addMeleeAttackGoal(0, 1, 2.2);
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.5F));
        super.registerGoals();
    }

    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.idle.startIfStopped(tickCount);
        }
        if (this.getTarget() != null) {
            if (this.tickCount % 600 == 0) {
                this.roar();
            }
            if (this.isFlag(0))
                this.selectFlags(this.getTarget());
        }
        if (this.isFlag(1))
            this.attackTick();
        else if (this.isFlag(2))
            this.summonTick();
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_PLUM_FLAGS.equals(pKey) && this.level().isClientSide) {
            switch (this.getPlumFlag()) {
                case 0: break;
                case 1: {
                    this.stopAllAnimations();
                    this.attack.startIfStopped(tickCount);
                    break;
                }
                case 2: {
                    this.stopAllAnimations();
                    this.summon.startIfStopped(tickCount);
                    break;
                }
                case 3: {
                    this.stopAllAnimations();
                    this.explode.startIfStopped(tickCount);
                    break;
                }
                default: {
                    this.setPlumFlag(0);
                    BlueOceans.LOGGER.warn("Can't handle synced event in {}.", this.getClass().getSimpleName());
                    break;
                }
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    private void selectFlags(LivingEntity pTarget) {
        float chance = randomUtil.nextFloat();
        if (this.closerThan(pTarget, 4)) {
            if (chance < 0.7F)
                this.setPlumFlag(1);
            else
                this.setPlumFlag(2);
        }
    }

    private void attackTick() {
        increaseAniTick();
        if (this.aniTickEquals(15)) {
            MobUtil.areaAttack(this, 2, 2, 90, 10.0F, 0.05F,
                    10, this.damageSources().mobAttack(this), false, entity ->
                    this.heal(1F));
        }
        if (this.aniTick(25)) {
            this.resetState();
        }
    }

    private void summonTick() {
        increaseAniTick();
        if (this.aniTickEquals(25)) {
            MobUtil.areaAttack(this, 3, 3, 90, 10.0F, 0.05F,
                    8, this.damageSources().mobAttack(this), true, entity -> {
                        this.heal(2F);
                        entity.setSecondsOnFire(2);
                    });
            this.playSound(SoundEvents.SCULK_SHRIEKER_SHRIEK);
            this.spawnBreedMob(this);
        }
        if (this.aniTick(30)) {
            this.resetState();
        }
    }

    @SuppressWarnings("unused")
    private void explodeTick() {
        increaseAniTick();
    }

    public void roar() {
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(4), this::canAttack);
        if (!entities.isEmpty()) {
            for (LivingEntity entity : entities) {
                this.doHurtTarget(entity);
                this.push(entity);
                this.heal(0.2F);
            }
        }
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 0));
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 1));
        this.playSound(SoundEvents.RAVAGER_ROAR);
        if (!this.level().isClientSide) {
            this.particleUtil.sendParticles(ParticleTypes.POOF, 20, 0.1, 0.1, 0.1,
                    0.25);
        }
    }

    protected void doAttackTarget(LivingEntity pEntity) {
        super.doAttackTarget(pEntity);
        this.heal(1F + this.getInfectLevel() * 0.5F);
    }

    public int getFlag() {
        return this.getPlumFlag();
    }

    public int getAniTick() {
        return this.entityData.get(DATA_ANI_TICK);
    }

    public void setFlag(int flag) {
        this.setPlumFlag(flag);
    }

    public void setAniTick(int aniTick) {
        this.entityData.set(DATA_ANI_TICK, aniTick);
    }

    public int getLevel() {
        return 2;
    }

    public void moveToBuilderTick() {
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.RAVAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SCULK_SHRIEKER_SHRIEK;
    }

    public float getVoicePitch() {
        return 0.5F;
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return null;
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(attack, summon);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 90).add(Attributes.ARMOR, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.29).add(Attributes.FOLLOW_RANGE, 56)
                .add(Attributes.ATTACK_DAMAGE, 12).add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    static {
        DATA_ANI_TICK = SynchedEntityData.defineId(RedDemon.class, EntityDataSerializers.INT);
    }
}
