
package com.bilibili.player_ix.blue_oceans.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class BoBrews {
    public static void addBrews() {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(Items.SPLASH_POTION), Ingredient.of(Items.SCULK_SENSOR),
                new ItemStack(BlueOceansItems.ECHO_POTION.get()));
    }
}
