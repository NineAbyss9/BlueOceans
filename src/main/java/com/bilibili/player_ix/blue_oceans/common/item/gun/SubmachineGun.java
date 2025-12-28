
package com.bilibili.player_ix.blue_oceans.common.item.gun;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.BulletProjectile;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SubmachineGun
extends AbstractGun {
    public SubmachineGun() {
        super(3000, Rarity.UNCOMMON);
    }

    public Vec3 bulletSpeed() {
        return new Vec3(3.0, 1.0, 3.0);
    }

    public float bulletDamage() {
        return 4.0F;
    }

    public boolean isNoCooling() {
        return true;
    }

    public void fire(ItemStack pStack, Level pLevel, LivingEntity pEntity) {
        BulletProjectile bullet = BulletProjectile.shoot(pEntity, this.bulletSpeed(), pLevel, this.bulletDamage());
        pLevel.addFreshEntity(bullet);
        if (!pLevel.isClientSide) {
            pLevel.playSound(null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), BlueOceansSounds
                    .SNIPER_RIFLE_FIRE.get(), pEntity.getSoundSource(), 1.0F, 1.0F);
            pStack.hurtAndBreak(1, pEntity, entity -> {
                Vec3 lookAngle = entity.getLookAngle();
                entity.setDeltaMovement(lookAngle.x * -1, entity.getDeltaMovement().y,
                        lookAngle.z * -1);
            });
        }
    }
}
