
package com.github.NineAbyss9.ix_api.api.mobs.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;

public class MoveToBlocksGoal
extends MoveToBlockGoal {
    private final Block[] blocks;
    public MoveToBlocksGoal(PathfinderMob pMob, double pSpeedModifier,
                            int pSearchRange, Block... validBlocks) {
        super(pMob, pSpeedModifier, pSearchRange);
        this.blocks = validBlocks;
    }

    public MoveToBlocksGoal(PathfinderMob pMob, double pSpeedModifier,
                            int pSearchRange, int pVerticalSearchRange, Block... validBlocks) {
        super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
        this.blocks = validBlocks;
    }



    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        boolean flag = false;
        for (Block block : blocks) {
            if (pLevel.getBlockState(pPos).is(block)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
