
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RedPlumGrassBlock extends RedPlumBlock {

    public Block getRestoreBlock(Level pLevel, BlockPos pPos) {
        return Blocks.GRASS_BLOCK;
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return false;
    }
}
