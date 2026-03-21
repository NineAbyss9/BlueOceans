
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Venom extends AbstractHurtingProjectile {
    public Venom(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public Venom(EntityType<? extends AbstractHurtingProjectile> type, double d, double d1, double dou,
                 double doll, double v, double e, Level world) {
        super(type, d, d1, dou, doll, v, e, world);
        this.xPower = doll;
        this.yPower = v;
        this.zPower = e;
    }

    public boolean isPickable() {
        return false;
    }

    public boolean isOnFire() {
        return false;
    }

    public float getLightLevelDependentMagicValue() {
        return 0;
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        Entity entity1 = this.getOwner();
        entity.hurt(this.damageSources().magic(), 6.0F);
        if (entity1 instanceof LivingEntity living) {
            this.doEnchantDamageEffects(living, entity);
        }
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (level().isClientSide)
            this.level().addParticle(ParticleTypes.WITCH, this.getX(), this.getY(), this.getZ(), 0.1,
                    0.5, 0.1);
        this.discard();
    }
}
