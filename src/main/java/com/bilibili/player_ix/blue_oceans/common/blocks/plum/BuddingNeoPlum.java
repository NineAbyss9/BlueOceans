
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.common.blocks.IPlumBlock;
import com.bilibili.player_ix.blue_oceans.common.blocks.RedPlumVein;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("deprecation")
public class BuddingNeoPlum
extends MultifaceBlock
implements SimpleWaterloggedBlock, IPlumBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = BoBlockProperties.AGE;
    private final MultifaceSpreader spreader;
    public BuddingNeoPlum(Properties pProperties) {
        super(pProperties);
        spreader = new MultifaceSpreader(new RedPlumVein.SpreaderConfig(this,
                MultifaceSpreader.DEFAULT_SPREAD_ORDER));
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED, AGE);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int age = pState.getValue(AGE);
        if (age > 0) {
            pState = pState.setValue(AGE, age - 1);
            pLevel.setBlockAndUpdate(pPos, pState);
        } else {
            pLevel.destroyBlock(pPos, false);
            MultifaceBlock block = (MultifaceBlock)BlueOceansBlocks.RED_PLUM_VEIN.get();
            BlockState state = block.getStateForPlacement(pState, pLevel, pPos, getFacing(pState));
            if (state != null)
                pLevel.setBlockAndUpdate(pPos, state);
        }
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pLevel.getBlockState(pPos).is(this))
            pLevel.scheduleTick(pPos, this, 1);
        if (pState.getValue(WATERLOGGED))
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public static Direction getFacing(BlockState pState) {
        for (Direction direction : DIRECTIONS) {
            if (hasFace(pState, direction))
                return direction;
        }
        return Direction.UP;
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.getItemInHand().is(this.asItem()) && super.canBeReplaced(pState, pUseContext);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) :
                Fluids.EMPTY.defaultFluidState();
    }

    public int getLevel() {
        return 0;
    }

    public MultifaceSpreader getSpreader() {
        return spreader;
    }
}
