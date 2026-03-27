
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class ShapedBlock
extends Block
{
    private VoxelShape shape;
    public ShapedBlock(Properties pProperties, VoxelShape pShape)
    {
        super(pProperties);
        this.shape = pShape;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return shape;
    }
}
