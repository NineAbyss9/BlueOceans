
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class WideMouthedBottle
extends AbstractContainer {
    public WideMouthedBottle(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    public int size() {
        return 2;
    }
}
