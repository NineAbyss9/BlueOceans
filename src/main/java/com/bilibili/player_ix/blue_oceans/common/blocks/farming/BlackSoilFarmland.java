
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlackSoilFarmland
extends FarmBlock {
    public BlackSoilFarmland(Properties pProperties) {
        super(pProperties);
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
    }
}
