
package com.bilibili.player_ix.blue_oceans.common.blocks.be;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PotEntity
extends BlockEntity {
    public PotEntity(BlockPos pPos, BlockState pBlockState) {
        super(null, pPos, pBlockState);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
    }
}
