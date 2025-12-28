
package com.bilibili.player_ix.blue_oceans.common.entities.boss;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.AbstractBlueOceansMob;
import com.github.player_ix.ix_api.api.mobs.ApiBoss;
import com.github.player_ix.ix_api.api.mobs.IFlagMob;
import com.github.player_ix.ix_api.util.UnmodifiableList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class MarbleGolem
extends AbstractBlueOceansMob
implements IFlagMob, ApiBoss, IAnimatedMob {
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK;
    public MarbleGolem(EntityType<? extends MarbleGolem> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
        this.entityData.define(DATA_ATTACK_TICK, 0);
    }

    public void tick() {
        super.tick();
        if (this.getTarget() != null) {
            this.selectFlag();
        }
    }

    public void aiStep() {
        super.aiStep();
        if (this.isFlag(0))
            return;
        if (this.isFlag(1))
            this.flag1();
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public int getAttackTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAttackTick(int attackTick) {
        this.entityData.set(DATA_ATTACK_TICK, attackTick);
    }

    private void selectFlag() {
    }

    private void flag1() {
        plusAttackTick();
        if (this.getAttackTick() > 40) {
            resetFlag();
            resetAttackTick();
        }
    }

    public List<AnimationState> getAllAnimations() {
        return UnmodifiableList.of();
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(MarbleGolem.class, EntityDataSerializers.INT);
        DATA_ATTACK_TICK = SynchedEntityData.defineId(MarbleGolem.class, EntityDataSerializers.INT);
    }
}
