
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.github.NineAbyss9.ix_api.api.mobs.Ownable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Thorn
extends Entity
implements Ownable {
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER;
    private static final EntityDataAccessor<Integer> DATA_TICK;
    public Thorn(EntityType<? extends Thorn> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_OWNER, Optional.empty());
    }

    public void tick() {
        super.tick();
        this.setTick(this.getTick() + 1);
        if (this.tickCount % 10 == 0 && this.isAlive() && !this.level().isClientSide)
            this.hurtEntities();
    }

    public void hurtEntities() {
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
        if (!entities.isEmpty())
            for (var entity : entities) {
                entity.hurt(this.damageSources().thorns(this), 2.0F);
            }
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.readOwnableAdditionalSaveData(pCompound);
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        this.addOwnableAdditionalSaveData(pCompound);
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNER).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNER, Optional.ofNullable(uuid));
    }

    public int getTick() {
        return this.entityData.get(DATA_TICK);
    }

    public void setTick(int pTick) {
        this.entityData.set(DATA_TICK, pTick);
    }

    static {
        DATA_OWNER = SynchedEntityData.defineId(Thorn.class, EntityDataSerializers.OPTIONAL_UUID);
        DATA_TICK = SynchedEntityData.defineId(Thorn.class, EntityDataSerializers.INT);
    }
}
