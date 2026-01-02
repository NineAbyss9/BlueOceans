
package com.bilibili.player_ix.blue_oceans.common.item.equipment;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IElement;
import com.bilibili.player_ix.blue_oceans.util.MathUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class ElementArmor
extends ArmorItem
implements IElement {
    private final Element element;
    public ElementArmor(Element pElement, ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
        this.element = pElement;
    }

    public Element getElement() {
        return element;
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (slot.equals(EquipmentSlot.HEAD) || slot.equals(EquipmentSlot.CHEST) || slot.equals(EquipmentSlot.FEET))
            return BlueOceans.armor(this.material.getName()).replace("helmet", "")
                    .replace("chestplate", "").replace("boots", "");
        else if (slot.equals(EquipmentSlot.LEGS))
            return BlueOceans.leggings(this.material.getName()).replace("leggings", "");
        return null;
    }

    public ElementArmor(Element pElement, SoundEvent pSound, Ingredient ingredient, String name, Type pType,
                        Properties properties) {
        this(pElement, MathUtils.getElementArmor(pElement, pType, pSound, ingredient, name), pType, properties);
    }

    public static ElementArmor helmet(Element pElement, SoundEvent pSound, Ingredient ingredient, String name,
                                      Properties properties) {
        return new ElementArmor(pElement, pSound, ingredient, name, Type.HELMET, properties);
    }

    public static ElementArmor chestplate(Element pElement, SoundEvent pSound, Ingredient ingredient, String name,
                                          Properties properties) {
        return new ElementArmor(pElement, pSound, ingredient, name, Type.CHESTPLATE, properties);
    }

    public static ElementArmor leggings(Element pElement, SoundEvent pSound, Ingredient ingredient, String name,
                                        Properties properties) {
        return new ElementArmor(pElement, pSound, ingredient, name, Type.LEGGINGS, properties);
    }

    public static ElementArmor boots(Element pElement, SoundEvent pSound, Ingredient ingredient, String name,
                                     Properties properties) {
        return new ElementArmor(pElement, pSound, ingredient, name, Type.BOOTS, properties);
    }
}
