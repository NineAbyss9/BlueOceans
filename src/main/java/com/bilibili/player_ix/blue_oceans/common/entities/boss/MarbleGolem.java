
package com.bilibili.player_ix.blue_oceans.common.entities.boss;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.AbstractBlueOceansMob;
import com.github.NineAbyss9.ix_api.api.mobs.ApiBoss;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import com.github.NineAbyss9.ix_api.util.UnmodifiableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MarbleGolem
extends AbstractBlueOceansMob
implements IFlagMob, ApiBoss, IAnimatedMob {
    public static final String TRUE_NAME = "Wu1Wu2";
    private static final EntityDataAccessor<Boolean> DATA_WU1_WU2;
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK;
    public MarbleGolem(EntityType<? extends MarbleGolem> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_WU1_WU2, false);
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

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Wu1Wu2", this.isWu1Wu2());
    }

    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.setWu1Wu2(this.hasWu1Wu2Name());
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public int getAniTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAniTick(int aniTick) {
        this.entityData.set(DATA_ATTACK_TICK, aniTick);
    }

    private void selectFlag() {
    }

    private void flag1() {
        increaseAniTick();
        if (this.getAniTick() > 40) {
            resetState();
        }
    }

    public boolean hasWu1Wu2Name() {
        Component customName = this.getCustomName();
        if (customName == null)
            return false;
        return customName.getString().equals(TRUE_NAME);
    }

    public boolean isWu1Wu2() {
        return this.entityData.get(DATA_WU1_WU2);
    }

    public void setWu1Wu2(boolean flag) {
        this.entityData.set(DATA_WU1_WU2, flag);
    }

    public List<AnimationState> getAllAnimations() {
        return UnmodifiableList.of();
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    static {
        DATA_WU1_WU2 = SynchedEntityData.defineId(MarbleGolem.class, EntityDataSerializers.BOOLEAN);
        DATA_FLAGS = SynchedEntityData.defineId(MarbleGolem.class, EntityDataSerializers.INT);
        DATA_ATTACK_TICK = SynchedEntityData.defineId(MarbleGolem.class, EntityDataSerializers.INT);
    }
}
