
package com.bilibili.player_ix.blue_oceans.api.item;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.bilibili.player_ix.blue_oceans.util.MathUtils;
import com.github.player_ix.ix_api.util.ItemUtil;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

public enum BoTier implements Tier {
    FLINT(72, 3.0F, 0.5F, 0, 7, Ingredient.of(Items.FLINT)),
    ICE(500, 6.09F, 4.6F, 2, 16,
            Ingredient.of(Items.ICE, Items.PACKED_ICE, Items.BLUE_ICE)),
    IRON(550, 6.0F, 2.0F, 2, 14, Ingredient.of(Items.IRON_INGOT)),
    RED_PLUM(1999, 7.9F, 5.0F, 3, 14,
            Ingredient.EMPTY);
    final int uses;
    final int level;
    final int ev;
    final float speed;
    final float damage;
    final LazyOptional<Ingredient> ingredient;
    BoTier(int pUses, float diggingSpeed, float pDamage, int pLevel, int enchantmentValue, Ingredient craft) {
        uses = pUses;
        speed = diggingSpeed;
        damage = pDamage;
        level = pLevel;
        ev = enchantmentValue;
        ingredient = LazyOptional.of(() -> craft);
    }

    public int getUses() {
        return uses;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAttackDamageBonus() {
        return damage;
    }

    public int getLevel() {
        return level;
    }

    public int getEnchantmentValue() {
        return ev;
    }

    public Ingredient getRepairIngredient() {
        return ingredient.orElse(Ingredient.EMPTY);
    }

    public static Tier get(Element pElement, float pDamage, int pLevel, int pEv, Ingredient pCraft) {
        return ItemUtil.getTier(MathUtils.getElementUses(pElement), MathUtils.getElementDiggingSpeed(pElement),
                pDamage, pLevel, pEv, pCraft);
    }

    public static Tier get(Element pElement, int pLevel, int pEv, String pName) {
        return get(pElement, MathUtils.getElementDamage(pElement), pLevel, pEv,
                Ingredient.of(ForgeRegistries.ITEMS.getValue(BlueOceans.location(pName))));
    }
}
