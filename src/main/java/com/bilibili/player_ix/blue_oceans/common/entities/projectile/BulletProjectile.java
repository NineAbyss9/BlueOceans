
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.bilibili.player_ix.blue_oceans.util.BoParticleUtil;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class BulletProjectile
extends AbstractHurtingProjectile {
    private static final EntityDataAccessor<Integer> DATA_LIFE;
    private float damage = 5F;
    public BulletProjectile(EntityType<? extends BulletProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BulletProjectile(double pX, double pY, double pZ, double pOffsetX, double pOffsetY,
                            double pOffsetZ, Level pLevel) {
        super(BlueOceansEntities.BULLET.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_LIFE, 600);
    }

    public int getLife() {
        return this.entityData.get(DATA_LIFE);
    }

    public void setLife(int pLife) {
        this.entityData.set(DATA_LIFE, pLife);
    }

    public void tick() {
        super.tick();
        this.setLife(this.getLife() - 1);
        if (this.getLife() <= 0) {
            this.discard();
        }
    }

    public static BulletProjectile shoot(LivingEntity pEntity, Vec3 pSpeed, Level pLevel, float pDamage) {
        Vec3 viewVector = pEntity.getViewVector(1.0F);
        BulletProjectile bulletProjectile = new
                BulletProjectile(pEntity.getX() + viewVector.x / 2, pEntity.getEyeY() - 1,
                pEntity.getZ() + viewVector.z / 2, viewVector.x * pSpeed.x,
                viewVector.y, viewVector.z * pSpeed.z, pLevel);
        bulletProjectile.setOwner(pEntity);
        bulletProjectile.setDamage(pDamage);
        bulletProjectile.updateRotation();
        //bulletProjectile.setXRot(pEntity.getXRot());
        //bulletProjectile.setYRot(pEntity.getYRot());
        return bulletProjectile;
    }

    public void setDamage(float pDamage) {
        this.damage = pDamage;
    }

    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        double y = entity.getEyeY();
        if (this.getOwner() instanceof LivingEntity livingEntity) {
            float finalDamage = this.damage + (pResult.getLocation().y >= y ? 20F : 0F);
            entity.hurt(this.damageSources().mobProjectile(this, livingEntity), finalDamage);
            this.doEnchantDamageEffects(livingEntity, entity);
        }
        this.discard();
    }

    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide) {
            BoParticleUtil.spark((ServerLevel)this.level(), pResult.getBlockPos().getCenter());
            this.playSound(BlueOceansSounds.BULLET_HIT.get());
        }
        super.onHitBlock(pResult);
        this.discard();
    }

    static {
        DATA_LIFE = SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.INT);
    }
}
