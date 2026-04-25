
package com.bilibili.player_ix.blue_oceans.common.blocks.core;

import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.AbstractContainer;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPipe
extends AbstractContainer
implements SimpleWaterloggedBlock {
    protected static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected AbstractPipe(Properties pProperties) {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
    }

    public Content getContent() {
        return Content.WATER;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        var level = pContext.getLevel();
        var pos = pContext.getClickedPos();
        if (level.getBlockState(pos).is(Blocks.WATER)) {
            this.holder.fill(Content.WATER);
            return this.defaultBlockState().setValue(WATERLOGGED, true);
        }
        return this.defaultBlockState();
    }
}
