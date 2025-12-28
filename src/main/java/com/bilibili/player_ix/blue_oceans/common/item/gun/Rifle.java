
package com.bilibili.player_ix.blue_oceans.common.item.gun;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.BulletProjectile;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Rifle
extends AbstractGun {
    public Rifle() {
        super(1999, Rarity.COMMON);
    }

    public float bulletDamage() {
        return 8F;
    }

    public int getUseTime() {
        return 30;
    }

    public void fire(ItemStack pStack, Level pLevel, LivingEntity pEntity) {
        BulletProjectile bullet = BulletProjectile.shoot(pEntity, this.bulletSpeed(), pLevel, this.bulletDamage());
        if (!pLevel.isClientSide) {
            pLevel.addFreshEntity(bullet);
            pLevel.playSound(null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), BlueOceansSounds
                    .SNIPER_RIFLE_FIRE.get(), pEntity.getSoundSource(), 1.0F, 1.0F);
            pStack.hurtAndBreak(1, pEntity, entity -> {
                Vec3 lookAngle = entity.getLookAngle();
                entity.setDeltaMovement(lookAngle.x * -0.5, entity.getDeltaMovement().y,
                        lookAngle.z * -0.5);
            });
        }
    }
}
