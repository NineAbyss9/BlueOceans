
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.util.MobUtil;
import com.github.player_ix.ix_api.api.mobs.IFlagMob;
import com.github.player_ix.ix_api.api.mobs.IShieldUser;
import com.github.player_ix.ix_api.api.mobs.ai.goal.MeleeGoal;
import com.github.player_ix.ix_api.util.UnmodifiableList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class RedPlumSlayer
extends RedPlumMonster
implements IFlagMob, IAnimatedMob {
    public AnimationState attack = new AnimationState();
    public AnimationState circle = new AnimationState();
    public AnimationState summon = new AnimationState();
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK;
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    public RedPlumSlayer(EntityType<? extends RedPlumSlayer> type, Level level) {
        super(type, level);
        this.xpReward = XP_REWARD_LARGE;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACK_TICK, 0);
        this.entityData.define(DATA_FLAGS, 0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (pKey.equals(DATA_FLAGS) && this.level().isClientSide) {
            switch (this.getFlag()) {
                case 0: {
                    break;
                }
                case 1: {
                    this.stopAllAnimations();
                    this.attack.startIfStopped(tickCount);
                    break;
                }
                case 2: {
                    this.stopAllAnimations();
                    this.circle.startIfStopped(tickCount);
                    break;
                }
                case 3: {
                    this.stopAllAnimations();
                    this.summon.startIfStopped(tickCount);
                    break;
                }
                default: {
                    BlueOceans.LOGGER.warn("Can't handle synched event {} in {}", this.getFlag(),
                            this.getClass().getSimpleName());
                    break;
                }
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    public void aiStep() {
        super.aiStep();
        if (this.isFlag(0)) {
            if (this.getTarget() != null && this.closerThan(this.getTarget(), 4)) {
                this.selectorFlag();
            }
            return;
        }
        if (this.isFlag(1))
            this.flag1();
        if (this.isFlag(2))
            this.flag2();
        if (this.isFlag(3))
            this.flag3();
    }

    public void registerBehaviors() {
        super.registerBehaviors();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeGoal(this, 1.0));
        super.registerGoals();
    }

    //Empty now
    protected int nextConvertUpNeeds() {
        return 0x7fffffff;
    }

    //Empty now
    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return null;
    }

    public int getLevel() {
        return 2;
    }

    protected void doAttackTarget(Entity pEntity) {
        super.doAttackTarget(pEntity);
        this.heal(0.5F);
    }

    protected float getPlusLevelChance() {
        return 0.15F;
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public List<AnimationState> getAllAnimations() {
        return UnmodifiableList.of(attack, circle, summon);
    }

    private void selectorFlag() {
        Random vRandom = this.getRandomUtil();
        int next = vRandom.nextInt(6);
        if (next < 3) {
            this.setFlag(1);
        } else if (next < 5) {
            this.setFlag(2);
        } else {
            this.setFlag(3);
        }
    }

    private void flag1() {
        increaseAnimTick();
        if (this.animTickEquals(15)) {
            AABB aabb = MobUtil.getRange(this, 2, 2, 2, 2, 2, 2, 2);
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, aabb, this::canAttack);
            if (!entities.isEmpty()) {
                for (LivingEntity living : entities) {
                    this.doHurtTarget(living);
                }
            }
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
        }
        if (this.animTick(25)) {
            this.resetFlag();
            this.resetAnimTick();
        }
    }

    private void flag2() {
        increaseAnimTick();
        if (this.animTickEquals(10) || this.animTickEquals(15)
                || this.animTickEquals(20)) {
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()
                    .inflate(3), this::canAttack);
            if (!entities.isEmpty()) {
                for (LivingEntity living : entities)
                    this.doHurtTarget(living);
            }
        }
        if (this.animTick(30)) {
            this.resetFlag();
            this.resetAnimTick();
        }
    }

    private void flag3() {
        increaseAnimTick();
        if (this.animTickEquals(25)) {
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()
                    .inflate(4), this::canAttack);
            if (!entities.isEmpty()) {
                for (LivingEntity living : entities) {
                    if (living.hurt(this.damageSources().mobAttack(this), 20F)) {
                        this.heal(1.0F);
                        IShieldUser.hurtShield(living, 15);
                    }
                }
            }
            this.playSound(SoundEvents.SCULK_SHRIEKER_SHRIEK);
            if (!this.level().isClientSide) {
                var plum = NeoPlum.create(this.position(), this.level());
                if (plum != null) {
                    NeoPlum.addParticleAroundPlum(plum);
                }
            }
        }
        if (this.animTick(30)) {
            this.resetFlag();
            this.resetAnimTick();
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SCULK_CATALYST_BLOOM;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SCULK_BLOCK_HIT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SCULK_SHRIEKER_SHRIEK;
    }

    public boolean canDisableShield() {
        return super.canDisableShield() || this.getRandomUtil().nextInt(3) == 0;
    }

    public int getAnimTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAnimTick(int animTick) {
        this.entityData.set(DATA_ATTACK_TICK, animTick);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 55).add(Attributes.ARMOR, 4)
                .add(Attributes.ATTACK_KNOCKBACK, 1).add(Attributes.KNOCKBACK_RESISTANCE,
                        0.25).add(Attributes.ATTACK_DAMAGE, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.27);
    }

    static {
        DATA_ATTACK_TICK = SynchedEntityData.defineId(RedPlumSlayer.class, EntityDataSerializers.INT);
        DATA_FLAGS = SynchedEntityData.defineId(RedPlumSlayer.class, EntityDataSerializers.INT);
    }
}
