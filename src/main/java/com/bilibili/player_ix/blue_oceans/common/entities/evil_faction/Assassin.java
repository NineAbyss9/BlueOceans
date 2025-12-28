
package com.bilibili.player_ix.blue_oceans.common.entities.evil_faction;

import com.bilibili.player_ix.blue_oceans.api.ai.goal.BOAttackTargetGoal;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.MeleeBehavior;
import com.github.player_ix.ix_api.api.mobs.IFlagMob;
import com.github.player_ix.ix_api.api.mobs.OwnableMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class Assassin
extends EvilFactionMember
implements IAnimatedMob, IFlagMob {
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    private int attackTick;
    public AnimationState attack = new AnimationState();
    public AnimationState trust = new AnimationState();
    public Assassin(EntityType<? extends Assassin> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
    }

    protected void registerGoals() {
        OwnableMob.addBehaviorGoals(this, 3, 0.8, 10F, true, false);
        super.registerGoals();
        this.targetSelector.addGoal(2, new BOAttackTargetGoal(this, false));
    }

    public void aiStep() {
        super.aiStep();
    }

    public void registerBehaviors() {
        this.behaviorSelector.addBehavior(1, new MeleeBehavior(this, 1.0));
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public int getAttackTick() {
        return attackTick;
    }

    public void setAttackTick(int attackTick) {
        this.attackTick = attackTick;
    }

    public List<AnimationState> getAllAnimations() {
        return List.of();
    }

    public IllagerArmPose getArmPose() {
        if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        }
        return IllagerArmPose.CROSSED;
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Assassin.class, EntityDataSerializers.INT);
    }
}
