
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.land;

import com.bilibili.player_ix.blue_oceans.common.blocks.food.Crop;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

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

    public static Supplier<Crop> simpleSupplier(Block pGrowth, int pMaxAge)
    {
        return () -> new SeedBlock(Properties.copy(pGrowth), pGrowth, pMaxAge);
    }
}
