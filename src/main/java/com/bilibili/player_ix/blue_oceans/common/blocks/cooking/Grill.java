
package com.bilibili.player_ix.blue_oceans.common.blocks.cooking;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.cooking.GrillEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Grill
extends BaseEntityBlock {
    public Grill(Properties pProperties) {
        super(pProperties);
    }

    public Grill() {
        this(Properties.of().strength(2.0F, 5.0F));
    }

    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GrillEntity(blockPos, blockState);
    }
}
