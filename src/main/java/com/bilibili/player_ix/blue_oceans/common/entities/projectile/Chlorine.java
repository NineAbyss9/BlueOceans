
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.github.player_ix.ix_api.api.mobs.ApiEntityDataSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Chlorine
extends Entity {
    private static final EntityDataAccessor<Vec3> DATA_RANGE;
    public Chlorine(EntityType<? extends Chlorine> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_RANGE, new Vec3(1, 1, 1));
    }

    public void tick() {
        super.tick();
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(
                this.getRange().x, this.getRange().y, this.getRange().z));
        if (!list.isEmpty()) {
            list.forEach(entity -> entity.hurt(this.damageSources().onFire(), 1.0F));
        }
    }

    public Vec3 getRange() {
        return this.entityData.get(DATA_RANGE);
    }

    public void setRange(Vec3 value) {
        this.entityData.set(DATA_RANGE, value);
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    static {
        DATA_RANGE = SynchedEntityData.defineId(Chlorine.class, ApiEntityDataSerializers.VEC3);
    }
}
