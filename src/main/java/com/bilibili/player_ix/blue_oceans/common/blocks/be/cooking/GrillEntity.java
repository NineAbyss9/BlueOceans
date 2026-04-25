
package com.bilibili.player_ix.blue_oceans.common.blocks.be.cooking;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GrillEntity
extends BlockEntity
implements WorldlyContainer {
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    protected int litTime;
    protected int cookingProgress;
    protected int cookingTotalTime;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType
            = RecipeType.SMOKING;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;
    public GrillEntity(BlockPos pPos, BlockState pBlockState) {
        super(null, pPos, pBlockState);
        this.quickCheck = RecipeManager.createCheck(RecipeType.SMOKING);
    }

    public static void tick(Level pLevel, BlockPos pPos, GrillEntity pEntity) {
        if (pLevel.isClientSide) {

        } else {

        }
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getItem(int pIndex) {
        return items.get(pIndex);
    }

    public ItemStack getPuttedItem() {
        return items.get(0);
    }

    public ItemStack getResults() {
        return items.get(2);
    }

    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(items, pIndex, pCount);
    }

    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(items, pSlot);
    }

    public void setItem(int pIndex, ItemStack pStack) {
        ItemStack itemstack = this.items.get(pIndex);
        boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, pStack);
        this.items.set(pIndex, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
        if (pIndex == 0 && !flag) {
            this.cookingTotalTime = getTotalCookTime(this.level, this);
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    public void clearContent() {
        this.items.clear();
    }

    public boolean stillValid(Player player) {
        return true;
    }

    static int getTotalCookTime(Level pLevel, GrillEntity pBlockEntity) {
        return pBlockEntity.quickCheck.getRecipeFor(pBlockEntity, pLevel).map(
                AbstractCookingRecipe::getCookingTime).orElse(200);
    }

    public int[] getSlotsForFace(Direction direction) {return new int[0];}
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {return false;}
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {return false;}
}
