
package com.bilibili.player_ix.blue_oceans.common.blocks.be;

import com.bilibili.player_ix.blue_oceans.common.blocks.WoodenSupport;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlockEntities;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.github.NineAbyss9.ix_api.util.Maths;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.NineAbyss9.util.*;

import javax.annotation.Nullable;
import java.util.List;

public class WoodenSupportBlockEntity
extends BlockEntity
implements WorldlyContainer, RecipeHolder, IXUtilUser {
    /**0 -> putted items
     * 1 -> fuels(deprecated)
     * 2 -> results*/
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    protected int litTime;
    protected int cookingProgress;
    protected int cookingTotalTime;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType
            = RecipeType.SMOKING;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;
    public WoodenSupportBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlueOceansBlockEntities.WOODEN_SUPPORT.get(), pPos, pBlockState);
        quickCheck = RecipeManager.createCheck(RecipeType.SMOKING);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        this.litTime = pTag.getInt("BurnTime");
        this.cookingProgress = pTag.getInt("CookTime");
        this.cookingTotalTime = pTag.getInt("CookTimeTotal");
        CompoundTag compoundtag = pTag.getCompound("RecipesUsed");
        for (String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("BurnTime", this.litTime);
        pTag.putInt("CookTime", this.cookingProgress);
        pTag.putInt("CookTimeTotal", this.cookingTotalTime);
        ContainerHelper.saveAllItems(pTag, this.items);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((location, integer) ->
                compoundtag.putInt(location.toString(), integer));
        pTag.put("RecipesUsed", compoundtag);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState,
                                  WoodenSupportBlockEntity pBlockEntity) {
        boolean flag = pBlockEntity.isBurning();
        boolean flag1 = false;
        boolean puttedItemsNotEmpty = !pBlockEntity.getPuttedItem().isEmpty();
        boolean fuelsReady = pLevel.getBlockState(pPos.below()).is(BoTags.FUELS);
        if (pBlockEntity.isBurning() || fuelsReady && puttedItemsNotEmpty) {
            Recipe<?> recipe;
            if (puttedItemsNotEmpty)
                recipe = pBlockEntity.quickCheck.getRecipeFor(pBlockEntity, pLevel).orElse(null);
            else
                recipe = null;
            if (!pBlockEntity.isBurning() && pBlockEntity.canBurn(pLevel.registryAccess(), recipe, pBlockEntity)) {
                pBlockEntity.litTime = 999999;
                if (pBlockEntity.isBurning())
                    flag1 = true;
                    /*if (itemstack.hasCraftingRemainingItem())
                        pBlockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                    else if (flag3) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            pBlockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                        }
                    }*/
            }
            if (pBlockEntity.isBurning() && pBlockEntity.canBurn(pLevel.registryAccess(), recipe, pBlockEntity)) {
                ++pBlockEntity.cookingProgress;
                if (pBlockEntity.cookingProgress == pBlockEntity.cookingTotalTime) {
                    pBlockEntity.cookingProgress = 0;
                    pBlockEntity.cookingTotalTime = getTotalCookTime(pLevel, pBlockEntity);
                    if (pBlockEntity.burn(pLevel, pPos, pLevel.registryAccess(), recipe, pBlockEntity)) {
                        pBlockEntity.setRecipeUsed(recipe);
                    }
                    flag1 = true;
                }
            } else
                pBlockEntity.cookingProgress = 0;
        } else if (!pBlockEntity.isBurning() && pBlockEntity.cookingProgress > 0) {
            pBlockEntity.cookingProgress = Mth.clamp(pBlockEntity.cookingProgress - 2, 0,
                    pBlockEntity.cookingTotalTime);
        }
        if (flag != pBlockEntity.isBurning()) {
            flag1 = true;
            pState = pState.setValue(WoodenSupport.BURNING, pBlockEntity.isBurning());
            pLevel.setBlock(pPos, pState, 3);
        }
        if (flag1)
            setChanged(pLevel, pPos, pState);
    }

    private boolean canBurn(RegistryAccess pRegistryAccess, @Nullable Recipe<?> pRecipe,
                            WoodenSupportBlockEntity woodenSupport) {
        if (!woodenSupport.getPuttedItem().isEmpty() && pRecipe != null) {
            ItemStack itemstack = pRecipe.assemble(IXUtil.c.convert(this), pRegistryAccess);
            if (itemstack.isEmpty() || itemstack.is(Items.WET_SPONGE))
                return false;
            else {
                ItemStack results = woodenSupport.getResults();
                if (results.isEmpty())
                    return true;
                else if (!ItemStack.isSameItem(results, itemstack))
                    return false;
                else if (results.getCount() + itemstack.getCount() <= woodenSupport.getMaxStackSize()
                        && results.getCount() + itemstack.getCount() <= results.getMaxStackSize())
                    return true;
                else {
                    return results.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        } else
            return false;
    }

    private boolean burn(Level pLevel, BlockPos pos, RegistryAccess pRegistryAccess,
                         @Nullable Recipe<?> pRecipe, WoodenSupportBlockEntity woodenSupport) {
        if (pRecipe != null && this.canBurn(pRegistryAccess, pRecipe, woodenSupport)) {
            woodenSupport.getRecipesToAwardAndPopExperience((ServerLevel)pLevel, Vec3.atCenterOf(pos));
            ItemStack puttedItems = woodenSupport.getPuttedItem();
            ItemStack itemstack1 = pRecipe.assemble(IXUtil.c.convert(this), pRegistryAccess);
            ItemEntity itemEntity = createItemEntity(pLevel, pos, itemstack1.copy());
            pLevel.addFreshEntity(itemEntity);
            puttedItems.shrink(1);
            return true;
        } else {
            resetBurnState();
            return false;
        }
    }

    public boolean isBurning() {
        return this.litTime > 0;
    }

    public void resetBurnState() {
        this.litTime = 0;
    }

    private static ItemEntity createItemEntity(Level pLevel, BlockPos blockPos, ItemStack stack) {
        return new ItemEntity(pLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack,
                Maths.trueOrFalse(pLevel.random.nextDouble() * 0.35D), 0.2D,
                Maths.trueOrFalse(pLevel.random.nextDouble() * 0.35D));
    }

    public void dropItems(Level pLevel, BlockPos blockPos) {
        for (ItemStack itemStack : this.items) {
            ItemEntity itemEntity = createItemEntity(pLevel, blockPos, itemStack);
            itemEntity.setNoPickUpDelay();
            pLevel.addFreshEntity(itemEntity);
        }
        this.items.clear();
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

    private static int getTotalCookTime(Level pLevel, WoodenSupportBlockEntity pBlockEntity) {
        return pBlockEntity.quickCheck.getRecipeFor(pBlockEntity, pLevel).map(
                AbstractCookingRecipe::getCookingTime).orElse(200);
    }

    /*@NotCheckUnused
    protected int getBurnDuration(ItemStack pFuel) {
        if (pFuel.isEmpty()) {
            return 0;
        } else {
            return net.minecraftforge.common.ForgeHooks.getBurnTime(pFuel, this.recipeType);
        }
    }*/

    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        if (pIndex == 2) {
            return false;
        } else if (pIndex != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, this.recipeType) > 0 || pStack
                    .is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void clearContent() {
        this.items.clear();
    }

    public void setRecipeUsed(@Nullable Recipe<?> pRecipe) {
        if (pRecipe != null) {
            ResourceLocation resourcelocation = pRecipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer pPlayer) {
        List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(pPlayer.serverLevel(), pPlayer.position());
        pPlayer.awardRecipes(list);
        for (Recipe<?> recipe : list) {
            if (recipe != null) {
                pPlayer.triggerRecipeCrafted(recipe, this.items);
            }
        }
        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel pLevel, Vec3 pPopVec) {
        List<Recipe<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            pLevel.getRecipeManager().byKey(entry.getKey()).ifPresent((p_155023_) -> {
                list.add(p_155023_);
                createExperience(pLevel, pPopVec, entry.getIntValue(), ((AbstractCookingRecipe)p_155023_).getExperience());
            });
        }
        return list;
    }

    private static void createExperience(ServerLevel pLevel, Vec3 pPopVec, int pRecipeIndex, float pExperience) {
        int i = Mth.floor((float)pRecipeIndex * pExperience);
        float f = Mth.frac((float)pRecipeIndex * pExperience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }
        ExperienceOrb.award(pLevel, pPopVec, i);
    }

    public int[] getSlotsForFace(Direction pSide) {
        return new int[0];
    }

    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

    LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER && facing != null && !this.remove) {
            return switch (facing) {
                case UP -> handlers[0].cast();
                case DOWN -> handlers[1].cast();
                default -> handlers[2].cast();
            };
        }
        return super.getCapability(capability, facing);
    }

    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}
