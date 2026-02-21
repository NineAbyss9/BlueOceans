
package com.bilibili.player_ix.blue_oceans.util;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.github.NineAbyss9.ix_api.util.ItemUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import org.NineAbyss9.math.AbyssMath;

public class MathUtils {
    public static final float PI = 3.14159265358979323846260F;
    public MathUtils() {
    }

    public static float divingPi(float pValue) {
        return AbyssMath.divingPi(pValue);
    }

    public static float piDiving(float pValue) {
        return AbyssMath.piDiving(pValue);
    }

    public static float relativeFe(Element pElement) {
        return pElement.getRelativeAtomicMass() / Element.Fe.getRelativeAtomicMass();
    }

    public static float multiplyByFe(Element element, float value) {
        return relativeFe(element) * value;
    }

    public static int getElementUses(Element pElement) {
        return (int)(relativeFe(pElement) * 250);
    }

    public static float getElementDiggingSpeed(Element pElement) {
        return relativeFe(pElement) * 6.0F;
    }

    public static float getElementDamage(Element pElement) {
        return relativeFe(pElement) * 2.0F;
    }

    public static int max(float a) {
        return (int)Math.max(a, 1);
    }

    public static ArmorMaterial getElementArmor(Element pElement, ArmorItem.Type type,
                                                SoundEvent soundEvent, Ingredient ingredient, String name) {
        return ItemUtil.getMaterial(max(getElementUses(pElement)), max(relativeFe(pElement) *
                        ArmorMaterials.IRON.getDefenseForType(type)), max(multiplyByFe(pElement,
                ArmorMaterials.IRON.getEnchantmentValue())), soundEvent, ingredient, name,
                multiplyByFe(pElement, ArmorMaterials.IRON.getToughness()),
               multiplyByFe(pElement, ArmorMaterials.IRON.getKnockbackResistance()));
    }
}
