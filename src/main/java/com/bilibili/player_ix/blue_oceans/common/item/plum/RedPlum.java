
package com.bilibili.player_ix.blue_oceans.common.item.plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class RedPlum extends Item {
    public RedPlum() {
        super(new Properties().food(new FoodProperties.Builder().saturationMod(1f)
                .nutrition(3).effect(()-> new MobEffectInstance(BlueOceansMobEffects
                                .PLUM_INVADE.get(), 40, 2), 1f).build()));
    }
}
