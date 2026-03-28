
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.land;

import com.bilibili.player_ix.blue_oceans.common.blocks.food.Crop;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SeedBlock
extends Crop
{
    private final Block growth;
    public SeedBlock(Properties pProperties, Block pGrowth, int pMaxAge)
    {
        super(pProperties, pMaxAge);
        this.growth = pGrowth;
    }

    public BlockState getGrowthState(BlockState pOriginState, ServerLevel pLevel, BlockPos pPos)
    {
        return this.growth.defaultBlockState();
    }
}
