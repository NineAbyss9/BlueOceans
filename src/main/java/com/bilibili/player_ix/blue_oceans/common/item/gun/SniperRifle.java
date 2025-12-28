
package com.bilibili.player_ix.blue_oceans.common.item.gun;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.BulletProjectile;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SniperRifle
extends AbstractGun {
    private static final String CHARGED = "Charged";
    public SniperRifle() {
        super(2399, Rarity.RARE);
    }

    public Vec3 bulletSpeed() {
        return new Vec3(4, 1, 4);
    }

    public float bulletDamage() {
        return 20F;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    public int getUseDuration(ItemStack pStack) {
        return 7200;
    }

    public void fire(ItemStack pStack, Level pLevel, LivingEntity pEntity) {
        BulletProjectile bullet = BulletProjectile.shoot(pEntity, this.bulletSpeed(), pLevel, this.bulletDamage());
        if (!pLevel.isClientSide) {
            pLevel.addFreshEntity(bullet);
            pLevel.playSound(null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), BlueOceansSounds
                    .SNIPER_RIFLE_FIRE.get(), pEntity.getSoundSource(), 1.0F, 1.0F);
            pStack.hurtAndBreak(1, pEntity, entity -> {
                Vec3 lookAngle = entity.getLookAngle();
                entity.setDeltaMovement(lookAngle.x * -1, entity.getDeltaMovement().y,
                        lookAngle.z * -1);
            });
        }
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        this.fire(pStack, pLevel, pLivingEntity);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        this.fire(pStack, pLevel, pLivingEntity);
        return pStack;
    }

    public boolean useOnRelease(ItemStack pStack) {
        return pStack.is(this);
    }

    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        //this.fire(stack, entity.level(), entity);
    }
}
