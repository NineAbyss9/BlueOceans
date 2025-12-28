
package com.bilibili.player_ix.blue_oceans.api.crafting;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ForgeRecipe implements Recipe<Container> {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(Container pContainer, Level pLevel) {
        return false;
    }

    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return null;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return null;
    }

    public ResourceLocation getId() {
        return null;
    }

    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    public RecipeType<?> getType() {
        return null;
    }
}
