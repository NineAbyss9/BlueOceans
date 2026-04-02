
package com.bilibili.player_ix.blue_oceans.common.entities.projectile.city;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ElevatorSide
extends Entity {
    private static final EntityDataAccessor<Integer> DATA_;
    public ElevatorSide(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_, 0);
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setCount(pCompound.getInt("Count"));
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Count", this.getCount());
    }

    public int getCount()
    {
        return this.entityData.get(DATA_);
    }

    public void setCount(int count)
    {
        this.entityData.set(DATA_, count);
    }

    static {
        DATA_ = SynchedEntityData.defineId(ElevatorSide.class, EntityDataSerializers.INT);
    }
}
