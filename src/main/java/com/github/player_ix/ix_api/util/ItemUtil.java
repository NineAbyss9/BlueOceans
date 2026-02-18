
package com.github.player_ix.ix_api.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

@PAMAreNonnullByDefault
public class ItemUtil {
    public ItemUtil() {
    }

    public static boolean instabuild(Player player) {
        return player.getAbilities().instabuild;
    }

    public static void shrink(ItemStack stack, Entity entity, int count) {
        if (entity instanceof Player player) {
            if (!player.getAbilities().instabuild)
                stack.shrink(count);
        } else
            stack.shrink(count);
    }

    public static void shrink(ItemStack stack, Entity player) {
        shrink(stack, player, 1);
    }

    public static ItemStack getItemByHand(LivingEntity pEntity, InteractionHand pHand) {
        return pEntity.getItemInHand(pHand);
    }

    public static boolean isMainHandEmpty(LivingEntity pEntity) {
        return pEntity.getMainHandItem().isEmpty();
    }

    public static boolean isHandEmpty(LivingEntity pEntity, InteractionHand pHand) {
        return getItemByHand(pEntity, pHand).isEmpty();
    }

    public static Tier getTier(int uses, float speed, float damage, int level, int EV, Ingredient ingredient) {
        return new TierInstance(uses, speed, damage, level, EV, ingredient);
    }

    public static ArmorMaterial getMaterial(int pDurability, int pDefense, int value, SoundEvent event, Ingredient craft,
                                            String name, float toughness, float resistance) {
        return new ArmorMaterialInstance(pDurability, pDefense, value, event, craft, name, toughness, resistance);
    }

    public record TierInstance(int uses, float speed, float damage, int level, int ev, Ingredient ingredient)
            implements Tier {
        public int getUses() {
            return this.uses;
        }

        public float getSpeed() {
            return this.speed;
        }

        public float getAttackDamageBonus() {
            return this.damage;
        }

        public int getLevel() {
            return this.level;
        }

        public int getEnchantmentValue() {
            return this.ev;
        }

        public Ingredient getRepairIngredient() {
            return this.ingredient;
        }
    }

    public record ArmorMaterialInstance(int durability, int defense, int enchantmentValue, SoundEvent equipSound,
                                        Ingredient repairCraft, String name, float toughness,
                                        float knockbackResistance)
            implements ArmorMaterial {
        public int getDurabilityForType(ArmorItem.Type type) {
            return this.durability;
        }

        public int getDefenseForType(ArmorItem.Type type) {
            return this.defense;
        }

        public int getEnchantmentValue() {
            return this.enchantmentValue;
        }

        public SoundEvent getEquipSound() {
            return this.equipSound;
        }

        public Ingredient getRepairIngredient() {
            return this.repairCraft;
        }

        public String getName() {
            return this.name;
        }

        public float getToughness() {
            return this.toughness;
        }

        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    }
}
