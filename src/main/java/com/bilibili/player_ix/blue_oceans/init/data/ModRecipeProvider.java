
package com.bilibili.player_ix.blue_oceans.init.data;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class ModRecipeProvider
extends RecipeProvider
{
    public ModRecipeProvider(PackOutput pOutput)
    {
        super(pOutput);
    }

    protected void buildRecipes(Consumer<FinishedRecipe> pWriter)
    {
        //Food
        shaped(RecipeCategory.FOOD, BlueOceansItems.BROWN_MUSHROOM_SKEWER.get(), Items.STICK,
                Items.BROWN_MUSHROOM, "  x", " x ", "i  ", pWriter);
        shaped(RecipeCategory.FOOD, BlueOceansItems.BROWN_MUSHROOM_SKEWER.get(), Items.STICK,
                Items.BROWN_MUSHROOM, "  x", " x ", "i  ", pWriter);
        allCookMethods(RecipeCategory.FOOD, BlueOceansItems.C_B_M_S.get(), BlueOceansItems.BROWN_MUSHROOM_SKEWER
                        .get(), 0.2f, pWriter);
        allCookMethods(RecipeCategory.FOOD, BlueOceansItems.C_M_S.get(), BlueOceansItems.MUSHROOM_SKEWER
                .get(), 0.2f, pWriter);
    }

    public static void shaped(RecipeCategory pC, ItemLike pResult, ItemLike pItem, ItemLike pItem1,
                              String pattern1, String pattern2, String pattern3, Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(pC, pResult).define('i', pItem)
                .define('x', pItem1).pattern(pattern1).pattern(pattern2).pattern(pattern3)
                .save(pWriter);
    }

    public static void allCookMethods(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp, Consumer<FinishedRecipe> writer)
    {
        smelting(pC, result, itemIn, exp, writer);
        smoking(pC, result, itemIn, exp, writer);
        campfire(pC, result, itemIn, exp, writer);
    }

    public static void smelting(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp, Consumer<FinishedRecipe> writer)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(itemIn), pC, result,
                exp, 200).unlockedBy(getHasName(itemIn), has(itemIn)).save(writer);
    }

    public static void smoking(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp, Consumer<FinishedRecipe> writer)
    {
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(itemIn), pC, result,
                exp, 100).unlockedBy(getHasName(itemIn), has(itemIn)).save(writer);
    }

    public static void campfire(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp, Consumer<FinishedRecipe> writer)
    {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(itemIn), pC, result,
                exp, 150).unlockedBy(getHasName(itemIn), has(itemIn)).save(writer);
    }
}
