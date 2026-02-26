
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public abstract class AbstractFarmland
extends FarmBlock {
    public AbstractFarmland(Properties pProperties) {
        super(pProperties);
    }

    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
                                   IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(world, pos.relative(facing));
        return plantType.equals(PlantType.CROP) || plantType.equals(PlantType.PLAINS);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.growCrop(pRandom)) {
            this.growCrop(pLevel, pPos);
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    public boolean growCrop(RandomSource pRandom) {
        return pRandom.nextFloat() < 0.1F;
    }

    public void growCrop(Level pLevel, BlockPos pSoilPos) {
        BlockState cropState = pLevel.getBlockState(pSoilPos.above());
        if (cropState.getBlock() instanceof CropBlock cropBlock
                && !cropBlock.isMaxAge(cropState)) {
            cropBlock.growCrops(pLevel, pSoilPos, cropState);
        }
    }
}
