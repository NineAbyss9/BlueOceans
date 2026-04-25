
package com.bilibili.player_ix.blue_oceans.init.data;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
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
        //Util
        shaped(RecipeCategory.TOOLS, BlueOceansItems.WOODEN_SUPPORT.get(), Items.STICK,
                getHasName(Items.STICK), has(Items.STICK), "iii", "i i",
                "i i", pWriter);
        shaped(RecipeCategory.TOOLS, BlueOceansItems.IRON_LADDER.get(), Items.IRON_INGOT,
                getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT), "i i", "iii", "i i",
                pWriter);

        //Farming
        shaped(RecipeCategory.TOOLS, BlueOceansItems.SPRINKLER.get(), Items.IRON_INGOT, Items.WATER_BUCKET,
                getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT), "iii", "ixi",
                "   ", pWriter);

        shapeless(RecipeCategory.MISC, Items.PAPER, BlueOceansItems.REED.get(), pWriter);
        //Food
        shaped(RecipeCategory.FOOD, BlueOceansItems.BROWN_MUSHROOM_SKEWER.get(), Items.STICK,
                Items.BROWN_MUSHROOM, "has_bms", has(Items.BROWN_MUSHROOM), "  x", " x ", "i  ", pWriter);
        shaped(RecipeCategory.FOOD, BlueOceansItems.MUSHROOM_SKEWER.get(), Items.STICK,
                Items.RED_MUSHROOM, "has_ms", has(Items.RED_MUSHROOM), "  x", " x ", "i  ", pWriter);

        shapeless(RecipeCategory.FOOD, BlueOceansItems.SUGARCANE.get(), 2,
                Ingredient.of(Items.SUGAR_CANE, Items.SUGAR_CANE), pWriter);

        allCookMethods(RecipeCategory.FOOD, BlueOceansItems.C_B_M_S.get(), BlueOceansItems.BROWN_MUSHROOM_SKEWER
                        .get(), 0.2f, pWriter);
        allCookMethods(RecipeCategory.FOOD, BlueOceansItems.C_M_S.get(), BlueOceansItems.MUSHROOM_SKEWER
                .get(), 0.2f, pWriter);
    }

    /**Single*/
    public static void shapeless(RecipeCategory pRC, ItemLike pResult, ItemLike pMaterial, Consumer<FinishedRecipe> pWriter)
    {
        ShapelessRecipeBuilder.shapeless(pRC, pResult).requires(pMaterial)
                .unlockedBy(getHasName(pResult), has(pMaterial)).save(pWriter,
                        new ResourceLocation(BlueOceans.MOD_ID + ":" + pResult + "_shapeless"));
    }

    /**Single with count*/
    public static void shapeless(RecipeCategory pRC, ItemLike pResult, int pRCount, ItemLike pMaterial, Consumer<FinishedRecipe> pWriter)
    {
        ShapelessRecipeBuilder.shapeless(pRC, pResult).requires(pMaterial)
                .unlockedBy(getHasName(pResult), has(pMaterial)).save(pWriter);
    }

    public static void shapeless(RecipeCategory pRC, ItemLike pResult, int pCount, Ingredient pIngredient, Consumer<FinishedRecipe> pWriter)
    {
        ShapelessRecipeBuilder.shapeless(pRC, pResult, pCount).requires(pIngredient)
                .unlockedBy(getHasName(pResult), has(pIngredient.getItems()[0].getItem())).save(pWriter);
    }

    public static void shaped(RecipeCategory pC, ItemLike pResult, ItemLike pItem,
                              String cn, CriterionTriggerInstance instance, String pattern1, String pattern2,
                              String pattern3, Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(pC, pResult).define('i', pItem)
                .pattern(pattern1).pattern(pattern2).pattern(pattern3)
                .unlockedBy(cn, instance).save(pWriter);
    }

    public static void shaped(RecipeCategory pC, ItemLike pResult, ItemLike pItem, ItemLike pItem1,
                              String cn, CriterionTriggerInstance instance, String pattern1, String pattern2,
                              String pattern3, Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(pC, pResult).define('i', pItem)
                .define('x', pItem1).pattern(pattern1).pattern(pattern2).pattern(pattern3)
                .unlockedBy(cn, instance).save(pWriter);
    }

    public static void shaped(RecipeCategory pC, ItemLike pResult, int count, ItemLike pItem,
                              String cn, CriterionTriggerInstance instance, String pattern1, String pattern2,
                              String pattern3, Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(pC, pResult, count).define('i', pItem)
                .pattern(pattern1).pattern(pattern2).pattern(pattern3)
                .unlockedBy(cn, instance).save(pWriter);
    }

    public static void shaped(RecipeCategory pC, ItemLike pResult, int count, ItemLike pItem, ItemLike pItem1,
                              String cn, CriterionTriggerInstance instance, String pattern1, String pattern2,
                              String pattern3, Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(pC, pResult, count).define('i', pItem)
                .define('x', pItem1).pattern(pattern1).pattern(pattern2).pattern(pattern3)
                .unlockedBy(cn, instance).save(pWriter);
    }

    public static void allCookMethods(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp, Consumer<FinishedRecipe> writer)
    {
        String st = getHasName(itemIn);
        smelting(pC, result, itemIn, exp, st, writer);
        smoking(pC, result, itemIn, exp, st, writer);
        campfire(pC, result, itemIn, exp, st, writer);
    }

    public static void smelting(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp,
                                String pName, Consumer<FinishedRecipe> writer)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(itemIn), pC, result,
                exp, 200).unlockedBy(pName, has(itemIn)).save(writer,
                new ResourceLocation(BlueOceans.MOD_ID + ":" + result + "_smelting"));
    }

    public static void smoking(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp,
                               String pName, Consumer<FinishedRecipe> writer)
    {
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(itemIn), pC, result,
                exp, 100).unlockedBy(pName, has(itemIn)).save(writer,
                new ResourceLocation(BlueOceans.MOD_ID + ":" + result + "_smoking"));
    }

    public static void campfire(RecipeCategory pC, ItemLike result, ItemLike itemIn, float exp,
                                String pName, Consumer<FinishedRecipe> writer)
    {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(itemIn), pC, result,
                exp, 150).unlockedBy(pName, has(itemIn)).save(writer,
                new ResourceLocation(BlueOceans.MOD_ID + ":" + result + "_campfire"));
    }
}
