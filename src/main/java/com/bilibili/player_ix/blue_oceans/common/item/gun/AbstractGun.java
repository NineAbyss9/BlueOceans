
package com.bilibili.player_ix.blue_oceans.common.item.gun;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractGun
extends Item {
    public AbstractGun(Properties properties) {
        super(properties);
    }

    public AbstractGun(int pDurability, Rarity pRarity) {
        this(new Properties().durability(pDurability).rarity(pRarity)
                .craftRemainder(Items.IRON_INGOT));
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        this.performUse(pLevel, pPlayer, pUsedHand);
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pUseDuration) {
        if (this.isNoCooling()) {
            this.fire(pStack, pLevel, pLivingEntity);
        }
    }

    public boolean isNoCooling() {
        return false;
    }

    public Vec3 bulletSpeed() {
        return new Vec3(1, 1, 1);
    }

    public float bulletDamage() {
        return 5F;
    }

    public int getUseTime() {
        return 40;
    }

    public void performUse(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
    }

    public abstract void fire(ItemStack pStack, Level pLevel, LivingEntity pEntity);
}
