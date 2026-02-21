
package com.bilibili.player_ix.blue_oceans.common.entities.evil_faction;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class SwordMan
extends PathfinderMob {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    private static final EntityDataAccessor<Integer> DATA_ANIMATION_TICK;
    public AnimationState attack1 = new AnimationState();
    public AnimationState attack2 = new AnimationState();
    public AnimationState attack3 = new AnimationState();
    public AnimationState summon = new AnimationState();
    public SwordMan(EntityType<? extends SwordMan> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 10.0F));
        this.goalSelector.addGoal(6, new FloatGoal(this));
    }

    public void aiStep() {
        super.aiStep();
        if (this.getTarget() != null && this.isFlag(0)) {
            this.pickFlags();
        }
        if (isFlag(1))
            this.attack1Tick();
        else if (isFlag(2))
            this.attack2Tick();
        else if (isFlag(3))
            this.attack3Tick();
        else if (isFlag(4))
            this.summonTick();
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey) && this.level().isClientSide) {
            switch (this.getFlag()) {
                case 0: break;
                case 1: {
                    this.stopAllAnimations();
                    this.attack1.startIfStopped(tickCount);
                    break;
                }
                case 2: {
                    this.stopAllAnimations();
                    this.attack2.startIfStopped(tickCount);
                    break;
                }
                case 3: {
                    this.stopAllAnimations();
                    this.attack3.startIfStopped(tickCount);
                    break;
                }
                case 4: {
                    this.stopAllAnimations();
                    this.summon.startIfStopped(tickCount);
                    break;
                }
                default: {
                    LOGGER.warn("Can't handle synced event in {}, call Player_IX!", this.getClass().getSimpleName());
                    this.setFlag(0);
                    break;
                }
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public boolean isFlag(int flag) {
        return this.getFlag() == flag;
    }

    public int getAnimationTick() {
        return this.entityData.get(DATA_ANIMATION_TICK);
    }

    public void setAnimationTick(int tick) {
        this.entityData.set(DATA_ANIMATION_TICK, tick);
    }

    public void increaseAnimationTick() {
        this.setAnimationTick(this.getAnimationTick() + 1);
    }

    public boolean animationTickEquals(int tick) {
        return this.getAnimationTick() == tick;
    }

    public boolean animationTickGreaterThan(int tick) {
        return this.getAnimationTick() >= tick;
    }

    public void resetState() {
        this.setFlag(0);
        this.setAnimationTick(0);
    }

    private List<AnimationState> allAnimations() {
        return List.of(attack1, attack2, attack3);
    }

    public void stopAllAnimations() {
        allAnimations().forEach(AnimationState::stop);
    }

    public void attack1Tick() {
        increaseAnimationTick();
        if (this.animationTickEquals(20)) {
            this.moveForward();
            this.hurtEntities();
        }
        if (this.animationTickGreaterThan(40)) {
            this.resetState();
        }
    }

    public void attack2Tick() {
        increaseAnimationTick();
        if (this.animationTickEquals(15)) {
            this.moveForward();
            this.hurtEntities();
        }
        if (this.animationTickGreaterThan(35)) {
            this.resetState();
        }
    }

    public void attack3Tick() {
        increaseAnimationTick();

    }

    public void summonTick() {
        increaseAnimationTick();
        if (this.animationTickGreaterThan(45)) {
            this.resetState();
        }
    }

    public void pickFlags() {
        float rand = this.random.nextFloat();
        if (rand < 0.25F) {
            this.setFlag(1);
        } else if (rand < 0.5F) {
            this.setFlag(2);
        } else if (rand < 0.75F) {
            this.setFlag(3);
        }
    }

    public void moveForward() {
        Vec3 vector = this.getLookAngle();
        this.setDeltaMovement(vector.x * 2.0, this.getDeltaMovement().y, vector.z * 2.0);
    }

    public void hurtEntities() {
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(4), this::canAttack);
        if (!entities.isEmpty()) {
            for (LivingEntity entity : entities) {
                this.doHurtTarget(entity);
            }
        }
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason,
                                        @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setFlag(4);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(SwordMan.class, EntityDataSerializers.INT);
        DATA_ANIMATION_TICK = SynchedEntityData.defineId(SwordMan.class, EntityDataSerializers.INT);
    }

    private static final class MeleeGoal extends MeleeAttackGoal {
        public MeleeGoal(PathfinderMob pMob) {
            super(pMob, 1, false);
        }

        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        }
    }
}
