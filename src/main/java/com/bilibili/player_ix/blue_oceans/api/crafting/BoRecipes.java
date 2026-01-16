
package com.bilibili.player_ix.blue_oceans.api.crafting;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BoRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(
            ForgeRegistries.RECIPE_TYPES, BlueOceans.MOD_ID);
    public static final RegistryObject<RecipeType<ForgeRecipe>> FORGE = RECIPES.register("forge",
            () -> RecipeType.simple(BlueOceans.location("forge")));
}
