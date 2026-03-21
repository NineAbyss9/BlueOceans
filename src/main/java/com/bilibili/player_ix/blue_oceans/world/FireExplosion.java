
package com.bilibili.player_ix.blue_oceans.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;

public class FireExplosion
{
    private final Level level;
    private final BlockPos position;
    public FireExplosion(Level pLevel, BlockPos pPos)
    {
        this.level = pLevel;
        this.position = pPos;
    }

    public void trigger()
    {
        for (BlockPos blockpos2 : BlockPos.betweenClosed(this.position.offset(3, 1, 3),
                this.position.offset(-3, -1, -3))) {
            if (Math.random() < 0.3 && this.level.getBlockState(blockpos2).isAir() &&
                    this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                this.level.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.level, blockpos2));
            }
        }
    }
}
