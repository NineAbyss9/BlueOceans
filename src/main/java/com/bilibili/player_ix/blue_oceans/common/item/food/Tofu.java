
package com.bilibili.player_ix.blue_oceans.common.item.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class Tofu extends Item {
    public Tofu() {
        super(new Properties().stacksTo(64).food(new FoodProperties.Builder()
                .nutrition(3).saturationMod(1.5f).build()));
    }
}
