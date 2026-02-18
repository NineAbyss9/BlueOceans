
package com.bilibili.player_ix.blue_oceans.common.item.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.NineAbyss9.math.MathSupport;

public class ChemicalFertilizer
extends Item {
    public static final String FERTILITY = "Fertility";
    public ChemicalFertilizer(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        if (!level.isClientSide) {
            ItemStack stack = pContext.getItemInHand();
            for (int i = 2;i < 6;i++) {
                BlockPos pos1 = pos.relative(Direction.values()[i]);
                BlockState state = level.getBlockState(pos1);
                if (state.getBlock() instanceof CropBlock cropBlock
                    && getFertility(stack) < MathSupport.random.nextFloat()) {
                    cropBlock.performBonemeal((ServerLevel)level, level.random, pos1, state);
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    public static float getFertility(ItemStack pStack) {
        if (pStack.getTag() == null || !pStack.getTag().contains(FERTILITY))
            pStack.getOrCreateTag().putFloat(FERTILITY, 0.2F);
        return pStack.getOrCreateTag().getFloat(FERTILITY);
    }

    public static void setFertility(ItemStack pStack, float pFertility) {
        pStack.getOrCreateTag().putFloat(FERTILITY, pFertility);
    }
}
