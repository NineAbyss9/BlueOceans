
package com.bilibili.player_ix.blue_oceans.common.item.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class PeaPod extends Item {
    public PeaPod() {
        super(new Properties().food(new FoodProperties.Builder().saturationMod(0.6f)
                        .nutrition(2).build()).stacksTo(64));
    }
}
