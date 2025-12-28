
package com.bilibili.player_ix.blue_oceans.common.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Ginkgo extends Item {
    public Ginkgo() {
        super(new Properties().food(new FoodProperties.Builder().effect(()-> new MobEffectInstance(
                MobEffects.ABSORPTION), 0.1f).effect(()-> new MobEffectInstance(MobEffects.POISON),
                        0.1f).nutrition(2).saturationMod(1f).build())
                .rarity(Rarity.UNCOMMON).stacksTo(64));
    }
}
