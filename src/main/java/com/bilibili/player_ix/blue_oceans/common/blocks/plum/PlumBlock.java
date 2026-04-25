
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public interface PlumBlock {
    default int getLevel() {
        return 1;
    }

    default Block getRestoreBlock(Level pLevel, BlockPos pPos) {
        return Blocks.AIR;
    }
}
