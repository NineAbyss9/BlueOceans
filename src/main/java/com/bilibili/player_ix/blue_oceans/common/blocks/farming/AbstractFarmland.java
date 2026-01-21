
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractFarmland
extends FarmBlock {
    public AbstractFarmland(Properties pProperties) {
        super(pProperties);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.growCrop(pRandom)) {
            this.growCrop(pLevel, pPos);
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    public boolean growCrop(RandomSource pRandom) {
        return pRandom.nextInt(10) == 0;
    }

    public void growCrop(Level pLevel, BlockPos pSoilPos) {
        BlockState cropState = pLevel.getBlockState(pSoilPos.above());
        if (cropState.getBlock() instanceof CropBlock cropBlock
                && !cropBlock.isMaxAge(cropState)) {
            cropBlock.growCrops(pLevel, pSoilPos, cropState);
        }
    }
}
