
package com.bilibili.player_ix.blue_oceans.common.enchantment;

import com.bilibili.player_ix.blue_oceans.api.mob.BoMobType;
import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PlumKiller
extends Enchantment {
    public PlumKiller() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {
        if (pTarget instanceof RedPlumMob) {
            pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
        }
    }

    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        if (mobType == BoMobType.RED_PLUM)
            return level * 0.75F;
        return super.getDamageBonus(level, mobType, enchantedItem);
    }

    public boolean canEnchant(ItemStack pStack) {
        if (pStack.getItem() instanceof AxeItem)
            return true;
        return super.canEnchant(pStack);
    }

    public boolean isDiscoverable() {
        return false;
    }
}
