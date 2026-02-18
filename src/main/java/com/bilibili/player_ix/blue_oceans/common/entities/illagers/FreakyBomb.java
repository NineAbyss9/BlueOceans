
package com.bilibili.player_ix.blue_oceans.common.entities.illagers;

import com.github.player_ix.ix_api.api.mobs.Ownable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import org.NineAbyss9.util.ValueHolder;

import javax.annotation.Nullable;
import java.util.UUID;

public class FreakyBomb extends Entity implements Ownable {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID =
            SynchedEntityData.defineId(FreakyBomb.class, EntityDataSerializers.INT);
    private float explodePower = 1;
    private int fuseTime = 40;
    @Nullable
    LivingEntity owner;
    @Nullable
    UUID ownerUUID;

    public FreakyBomb(EntityType<? extends FreakyBomb> entityType, Level world) {
        super(entityType, world);
    }

    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }
        int $$0 = this.getFuse() - 1;
        this.setFuse($$0);
        if ($$0 <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(),
                        0.0, 0.0, 0.0);
            }
        }
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 0);
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    public int getFuse() {
        return this.fuseTime;
    }

    public void setFuse(int pFuseTime) {
        this.fuseTime = pFuseTime;
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void setOwner(@Nullable LivingEntity lie) {
        this.owner = lie;
        if (lie != null) this.setOwnerUUID(lie.getUUID());
    }

    public void setOwnerUUID(@Nullable UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public boolean isHostile() {
        return false;
    }

    public float getExplodePower() {
        return this.explodePower;
    }

    public void setExplodePower(float $float) {
        this.explodePower = $float;
    }

    private void explode() {
        this.level().explode(ValueHolder.nullToOther(this.getOwner(), this), this.getX(), this.getY(), this.getZ(),
                this.getExplodePower(), Level.ExplosionInteraction.MOB);
    }
}
