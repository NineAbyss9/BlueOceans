
package com.bilibili.player_ix.blue_oceans.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Harvest
extends Enchantment {
    public Harvest() {
        super(Rarity.UNCOMMON, EnchantmentCategory.VANISHABLE,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
}
