
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PlumCellClusterBlock
extends Block
implements PlumBlock {
    public static final IntegerProperty GROWTH_AGE = BoBlockProperties.GROWTH_AGE;
    static final VoxelShape SHAPE;
    public PlumCellClusterBlock() {
        super(Properties.of().instabreak().mapColor(DyeColor.RED).noCollission()
                .sound(SoundType.SCULK_VEIN));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(GROWTH_AGE);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        pLevel.scheduleTick(pPos, this, 20);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(GROWTH_AGE, 30);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int age = pState.getValue(GROWTH_AGE);
        if (age > 0) {
            BlockState state  = pState.setValue(GROWTH_AGE, age - 1);
            pLevel.setBlock(pPos, state, 2);
            pLevel.scheduleTick(pPos, this, 20);
        } else {
            pLevel.setBlockAndUpdate(pPos, BlueOceansBlocks.PLUM_TISSUE.get().defaultBlockState());
        }
    }

    static {
        SHAPE = box(0, 0, 0, 16, 2, 16);
    }
}
