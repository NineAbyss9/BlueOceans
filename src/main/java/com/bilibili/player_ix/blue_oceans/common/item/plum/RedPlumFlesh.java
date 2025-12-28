
package com.bilibili.player_ix.blue_oceans.common.item.plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class RedPlumFlesh extends Item {
    public RedPlumFlesh() {
        super(new Properties().stacksTo(64).food(new FoodProperties.Builder().meat().fast()
                .nutrition(4).saturationMod(2.5f).effect(
                        ()-> new MobEffectInstance(BlueOceansMobEffects.PLUM_INVADE.get(), 300,
                                0), 1).build()));
    }
}
