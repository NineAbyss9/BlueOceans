
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.api.mob.ISleepMob;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.SleepGoal;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.Level;

import java.util.List;

public class ModCat
extends BoAnimal
implements ISleepMob, IFlagMob, IAnimatedMob {
    public Cat cat;
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    public AnimationState idle = new AnimationState();
    public AnimationState sleep = new AnimationState();
    public ModCat(EntityType<? extends BoAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(tickCount);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey) && this.level().isClientSide) {
            switch (this.getFlag()) {
                case 0 -> {
                }
                case 1 -> {
                    stopAllAnimations();
                    sleep.startIfStopped(tickCount);
                }
                case 2 -> {
                    stopAllAnimations();
                }
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new SleepGoal<>(this));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.CAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    public boolean canSleep() {
        return this.level().isNight();
    }

    public boolean isSleeping() {
        return super.isSleeping();
    }

    public void setSleeping(boolean var0) {
        this.setFlag(var0 ? 1 : 0);
    }

    public void meow() {
        playSound(getAmbientSound());
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(sleep);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(ModCat.class, EntityDataSerializers.INT);
    }
}
