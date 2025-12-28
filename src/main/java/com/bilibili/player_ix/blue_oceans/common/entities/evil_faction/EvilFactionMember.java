
package com.bilibili.player_ix.blue_oceans.common.entities.evil_faction;

import com.bilibili.player_ix.blue_oceans.api.mob.EvilFactionMob;
import com.bilibili.player_ix.blue_oceans.api.mob.IBehaviorUser;
import com.bilibili.player_ix.blue_oceans.api.mob.ICitizen;
import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.Behavior;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorFlag;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorSelector;
import com.bilibili.player_ix.blue_oceans.government.Government;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;

public abstract class EvilFactionMember
extends AbstractIllager
implements EvilFactionMob, IBehaviorUser, ICitizen {
    protected final BehaviorSelector behaviorSelector;
    private static final EntityDataAccessor<Optional<BlockPos>> DATA_MEETING_POS;
    private static final EntityDataAccessor<Integer> DATA_TASK;
    public EvilFactionMember(EntityType<? extends EvilFactionMember> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.behaviorSelector = new BehaviorSelector(pLevel::getProfiler);
        this.registerBehaviors();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TASK, 0);
    }

    protected void customServerAiStep() {
        this.behaviorTick();
        super.customServerAiStep();
    }

    public void registerBehaviors() {
    }

    public BehaviorSelector getBehaviorSelector() {
        return behaviorSelector;
    }

    public Government getGovernment() {
        return Government.EVIL_FACTION;
    }

    public void setGovernment(Government newGovernment) {
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        this.addCitizenAdditionalSaveData(pCompound);
    }

    @Nullable
    public BlockPos getMeetingPos() {
        return this.entityData.get(DATA_MEETING_POS).orElse(null);
    }

    public void setMeetingPos(@Nullable BlockPos pPos) {
        this.entityData.set(DATA_MEETING_POS, Optional.ofNullable(pPos));
    }

    public void resetMeetingPos() {
        this.setMeetingPos(null);
    }

    public Task getTask() {
        return Task.fromId(this.entityData.get(DATA_TASK));
    }

    public void setTask(int pTask) {
        this.entityData.set(DATA_TASK, pTask);
    }

    public void applyRaidBuffs(int i, boolean b) {
    }

    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    static {
        DATA_TASK = SynchedEntityData.defineId(EvilFactionMember.class, EntityDataSerializers.INT);
        DATA_MEETING_POS = SynchedEntityData.defineId(EvilFactionMember.class,
                EntityDataSerializers.OPTIONAL_BLOCK_POS);
    }

    protected static class FollowTaskBehavior extends Behavior {
        protected final EvilFactionMember member;
        public FollowTaskBehavior(EvilFactionMember pMember) {
            this.member = pMember;
            this.setFlags(EnumSet.of(BehaviorFlag.TARGET, BehaviorFlag.MOVE));
        }

        public boolean canUse() {
            return member.getTask() != Task.EMPTY;
        }

        public boolean canContinueToUse() {
            return member.getTask() != Task.EMPTY;
        }

        public void start() {
            switch (member.getTask()) {
                case PURSUIT_AND_APPREHENSION: {
                    this.getNothing().noting();
                    break;
                }
                case MEET: {
                    this.meet();
                    break;
                }
            }
        }

        protected void meet() {

        }
    }
}
