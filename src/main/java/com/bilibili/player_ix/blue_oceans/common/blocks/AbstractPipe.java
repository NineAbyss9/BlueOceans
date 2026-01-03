
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class AbstractPipe
extends Block
implements SimpleWaterloggedBlock {
    protected static final BooleanProperty WATER_LOGGED = BlockStateProperties.WATERLOGGED;
    protected AbstractPipe(Properties pProperties) {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATER_LOGGED);
    }
}
