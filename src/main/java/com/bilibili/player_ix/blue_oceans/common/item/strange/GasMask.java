
package com.bilibili.player_ix.blue_oceans.common.item.strange;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.github.player_ix.ix_api.util.ItemUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class GasMask
extends ArmorItem {
    public GasMask(Properties pProperties) {
        super(ItemUtil.getMaterial(500, 1, 1, SoundEvents.ARMOR_EQUIP_LEATHER,
                Ingredient.EMPTY, "gas_mask", 0, 0), Type.HELMET, pProperties);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return BlueOceans.armor("gas_mask");
    }
}
