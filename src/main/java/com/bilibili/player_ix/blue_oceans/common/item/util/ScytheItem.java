
package com.bilibili.player_ix.blue_oceans.common.item.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ScytheItem
extends DiggerItem {
    public ScytheItem(float pDamageModifier, float pAttackSpeed, Tier pTier, Properties pProperties) {
        super(pDamageModifier, pAttackSpeed, pTier, BlockTags.CROPS, pProperties);
    }

    public static int shouldDrop(BlockState state) {
        if (state.getBlock() instanceof CropBlock crop) {
            return crop.isMaxAge(state) ? 1 : 0;
        } else if (state.is(BlockTags.CROPS)) {
            return 2;
        } else {
            return 0;
        }
    }

    public static boolean dropResources(BlockState state, Level level, BlockPos pPos, @Nullable Entity entity1,
                                     ItemStack stack) {
        int i = shouldDrop(state);
        if (i > 0) {
            int dropCount = level.random.nextInt(3);
            for (int j = 0; j < dropCount;j++)
                Block.dropResources(state, level, pPos, level.getBlockEntity(pPos), entity1, stack);
            if (i == 1) {
                CropBlock crop = ((CropBlock)state.getBlock());
                if (crop.getMaxAge() >= 1)
                    level.setBlock(pPos, crop.getStateForAge(1), 2);
                else
                    level.setBlock(pPos, crop.getStateForAge(0), 2);
            }
            return true;
        }
        return false;
    }

    public static boolean shouldDamage(ItemStack stack) {
        return stack.getEnchantmentLevel(Enchantments.UNBREAKING) < 1;
    }

    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        dropResources(pState, pLevel, pPos, pEntityLiving, pStack);
        if (pState.getBlock().defaultDestroyTime() <= 0.0F && shouldDamage(pStack))
            pStack.hurtAndBreak(1, pEntityLiving, entity -> entity.broadcastBreakEvent(
                    InteractionHand.MAIN_HAND));
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = pContext.getPlayer();
        ItemStack stack = pContext.getItemInHand();
        if (dropResources(state, level, pos, player, stack)) {
            if (shouldDamage(stack) && player != null)
                stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(pContext.getHand()));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(pContext);
    }
}
