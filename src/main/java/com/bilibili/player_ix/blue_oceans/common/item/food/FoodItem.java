
package com.bilibili.player_ix.blue_oceans.common.item.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.nine_abyss.util.function.CiFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodItem
extends Item {
    private final CiFunction<ItemStack, Level, Player, ItemStack> function;
    public FoodItem(Item.Properties properties,
                    CiFunction<ItemStack, Level, Player, ItemStack> pFunction) {
        super(properties);
        this.function = pFunction;
    }

    public FoodItem(Item.Properties properties) {
        this(properties, CiFunction.emptyA());
    }

    public FoodItem(int pNutrition, float pSaturation) {
        this(new Properties().stacksTo(64).food(
                new FoodProperties.Builder().nutrition(pNutrition).saturationMod(pSaturation).build()));
    }

    public FoodItem(int pNutrition, float pSaturation, Rarity pRarity) {
        this(new Properties().food(new FoodProperties.Builder().nutrition(pNutrition).saturationMod(pSaturation)
                .build()).rarity(pRarity));
    }

    public FoodItem(int pNutrition, float pSaturation, CiFunction<ItemStack, Level, Player, ItemStack> ciFunction) {
        this(new Properties().stacksTo(64).food(
                new FoodProperties.Builder().nutrition(pNutrition).saturationMod(pSaturation).build()), ciFunction);
    }

    public FoodItem(int pNutrition, float pSaturation, float probability, MobEffectInstance... effects) {
        this(new Properties().stacksTo(64).food(
                new BoFoodBuilder().addEffects(probability, effects).nutrition(pNutrition).saturationMod(pSaturation).build()));
    }

    public FoodItem(Rarity rarity, int pNutrition, float pSaturation, float probability, MobEffectInstance... effects) {
        this(new Properties().rarity(rarity).stacksTo(64).food(
                new BoFoodBuilder().addEffects(probability, effects).nutrition(pNutrition).saturationMod(pSaturation).build()));
    }

    public FoodItem(Rarity rarity, int pNutrition, float pSaturation, float probability, int pDuring,
                    int pLevel, MobEffect... effects) {
        this(new Properties().rarity(rarity).stacksTo(64).food(
                new BoFoodBuilder().addEffects(probability, pDuring, pLevel, effects).nutrition(pNutrition)
                        .saturationMod(pSaturation).build()));
    }

    public static FoodItem fast(int pNutrition, float pSaturation) {
        return new FoodItem(new Properties().food(new FoodProperties.Builder().nutrition(pNutrition)
                .saturationMod(pSaturation).fast().build()));
    }

    public static FoodItem comfortable(int pNutrition, float pSaturation) {
        return new FoodItem(new Properties().food(new FoodProperties.Builder().nutrition(pNutrition)
                .saturationMod(pSaturation).effect(() ->
                        new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN), 0.0F).build()));
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            ItemStack result = function.apply(pStack, pLevel, player);
            if (result == pStack)
                return super.finishUsingItem(pStack, pLevel, pLivingEntity);
            else
                return result;
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @SuppressWarnings("all")
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced) {
        FoodProperties properties = this.getFoodProperties();
        if (!properties.getEffects().isEmpty()) {
            List list = new ArrayList();
            for (Pair<MobEffectInstance, Float> pair : properties.getEffects()) {
                list.add(pair.getFirst());
            }
            PotionUtils.addPotionTooltip(list, pTooltipComponents, 1.0F);
        }
    }

    public static class BoFoodBuilder extends FoodProperties.Builder {
        public BoFoodBuilder addEffects(float p, MobEffectInstance... effects) {
            for (var mobEffect : effects) {
                this.effect(()-> mobEffect, p);
            }
            return this;
        }

        public BoFoodBuilder addEffects(float p, int pDuring, int pLevel, MobEffect... mobEffects) {
            for (var effect : mobEffects) {
                this.effect(()-> new MobEffectInstance(effect, pDuring, pLevel), p);
            }
            return this;
        }

        public BoFoodBuilder addEffects(int pDuring, int pLevel, Map<MobEffect, Float> mobEffects) {
            for (var entry : mobEffects.entrySet()) {
                this.effect(()-> new MobEffectInstance(entry.getKey(), pDuring, pLevel), entry.getValue());
            }
            return this;
        }

        public BoFoodBuilder addEffects(Map<MobEffectInstance, Float> mobEffects) {
            for (var entry : mobEffects.entrySet()) {
                this.effect(entry::getKey, entry.getValue());
            }
            return this;
        }
    }
}
