
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.init.data.ModBlockStateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class RedPlumGrass
extends BushBlock
implements PlumBlock, ModBlockStateProvider.Cross
{
    protected static final VoxelShape SHAPE;
    public RedPlumGrass() {
        super(Properties.of().mapColor(DyeColor.RED).replaceable().noCollission().instabreak()
                .sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava()
                .pushReaction(PushReaction.DESTROY).lightLevel(value -> 2));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Block.isFaceFull(pState.getShape(pLevel, pPos),  Direction.UP);
    }

    static {
        SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    }
}
