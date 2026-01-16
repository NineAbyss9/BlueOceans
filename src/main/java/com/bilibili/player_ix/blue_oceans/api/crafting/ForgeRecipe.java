
package com.bilibili.player_ix.blue_oceans.api.crafting;

//import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record ForgeRecipe(ResourceLocation id, int hitCount, Ingredient hitItem, ItemStack result)
implements Recipe<Container> {
    public static final Serializer SERIALIZER = new Serializer();

    public boolean matches(Container pContainer, Level pLevel) {
        return this.hitItem.test(pContainer.getItem(2));
    }

    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = pContainer.getItem(1).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }
        return itemstack;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 3 && pHeight >= 1;
    }

    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    public ResourceLocation getId() {
        return id;
    }

    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public RecipeType<?> getType() {
        return //RecipeType.simple(BlueOceans.location("forging"))
                null;
    }

    public static class Serializer implements RecipeSerializer<ForgeRecipe> {
        public ForgeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            int hitCount = pSerializedRecipe.getAsInt();
            Ingredient ingredient = Ingredient.fromJson(pSerializedRecipe);
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe,
                    "result"));
            return new ForgeRecipe(pRecipeId, hitCount, ingredient, itemstack);
        }

        public ForgeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int hitCount = pBuffer.readInt();
            Ingredient hitItem = Ingredient.fromNetwork(pBuffer);
            ItemStack stack = pBuffer.readItem();
            return new ForgeRecipe(pRecipeId, hitCount, hitItem, stack);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, ForgeRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.hitCount);
            pRecipe.hitItem.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
