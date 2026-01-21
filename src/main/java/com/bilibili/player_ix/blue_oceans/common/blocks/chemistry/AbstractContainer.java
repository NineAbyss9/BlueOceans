
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import com.bilibili.player_ix.blue_oceans.api.misc.ContentHolder;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("deprecation")
public abstract class AbstractContainer
extends BaseEntityBlock {
    protected static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected final ContentHolder holder;
    public AbstractContainer(Properties pProperties) {
        super(pProperties);
        holder = new ContentHolder();
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED))
            pLevel.scheduleTick(pPos, Fluids.WATER, 1);
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public boolean fill(Content c) {
        return this.holder().fill(c);
    }

    public ContentHolder holder() {
        return holder;
    }

    public int size() {
        return 1;
    }
}
