
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.water;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

//水棉,净化
@SuppressWarnings("deprecation")
public class WaterHyacinth
extends Block {
    public static final VoxelShape SHAPE = box(0, 0, 0, 16, 1, 16);
    public WaterHyacinth(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
