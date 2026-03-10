
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.NineAbyss9.ix_api.util.DirectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class RedPlumVein
extends MultifaceBlock
implements SimpleWaterloggedBlock, PlumBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final MultifaceSpreader veinSpreader;
    //private final MultifaceSpreader sameSpaceSpreader =
    //        new MultifaceSpreader(new SpreaderConfig(MultifaceSpreader.SpreadType.SAME_POSITION));
    public RedPlumVein(Properties properties) {
        super(properties);
        veinSpreader = new MultifaceSpreader(new SpreaderConfig(MultifaceSpreader.DEFAULT_SPREAD_ORDER));
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE));
    }

    public MultifaceSpreader getSpreader() {
        return this.veinSpreader;
    }

    public BlockState getSupportBlock(Level pLevel, BlockPos pVeinPos) {
        return pLevel.getBlockState(pVeinPos.relative(getFacing(pLevel.getBlockState(pVeinPos))));
    }

    public static Direction getFacing(BlockState pState) {
        for (Direction direction : DIRECTIONS) {
            if (hasFace(pState, direction))
                return direction;
        }
        return Direction.UP;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (RedPlumUtil.canSpreadPlum(pLevel) && grow(pRandom)) {
            BlockPos pos;
            for (int i = 0;i<4;i++) {
                if (DirectionUtil.isVertical(getFacing(pState))) {
                    if (i == 0) {
                        pos = pPos.offset(0, 1, 0);
                    } else if (i == 1) {
                        pos = pPos.offset(0, -1, 0);
                    } else if (i == 2) {
                        pos = pPos.relative(getFacing(pState));
                    } else {
                        pos = new BlockPos(pPos.getX() -getFacing(pState).getStepX(),
                                pPos.getY(), pPos.getZ() -getFacing(pState).getStepZ());
                    }
                } else {
                    if (i == 0) {
                        pos = pPos.offset(1, 0, 0);
                    } else if (i == 1) {
                        pos = pPos.offset(-1, 0, 0);
                    } else if (i == 2) {
                        pos = pPos.offset(0, 0, 1);
                    } else {
                        pos = pPos.offset(0, 0, -1);
                    }
                }
                BlockPos belowPos = pos.relative(getFacing(pState));
                BlockState state = pLevel.getBlockState(pos);
                BlockState belowState = pLevel.getBlockState(belowPos);
                if (state.canBeReplaced() && Block.isFaceFull(belowState
                        .getBlockSupportShape(pLevel, belowPos), getFacing(pState))) {
                    pLevel.destroyBlock(pos, false);
                    BlockState state1 = this.getGrowState(state, pLevel, pPos, getFacing(pState));
                    if (state1 != null)
                        pLevel.setBlockAndUpdate(pos, state1);
                }
            }
            if (isNeoPlum()) {
                pLevel.destroyBlock(pPos, false);
                MultifaceBlock block = (MultifaceBlock)BlueOceansBlocks.RED_PLUM_VEIN.get();
                pLevel.setBlockAndUpdate(pPos, block.getStateForPlacement(pState, pLevel, pPos, getFacing(pState)));
            } else
            {
                BlockPos relative = pPos.relative(getFacing(pState));
                if (!pLevel.getBlockState(relative).is(BoTags.RED_PLUM_BLOCKS)) {
                    pLevel.setBlockAndUpdate(relative, RedPlumCatalyst.getRandomGrowthState(pLevel, relative));
                    if (pLevel.getBlockState(pPos).is(BlueOceansBlocks.RED_PLUM_VEIN.get()))
                        pLevel.destroyBlock(pPos, false);
                }
            }
        }
    }

    public boolean grow(RandomSource pRandom) {
        return pRandom.nextFloat() < 0.1F;
    }

    @Nullable
    public BlockState getGrowState(BlockState pState, ServerLevel pLevel, BlockPos pPos, Direction pFacing) {
        MultifaceBlock block = (MultifaceBlock)BlueOceansBlocks.RED_PLUM_VEIN.get();
        return block.getStateForPlacement(pState, pLevel, pPos, pFacing);
    }

    public boolean isNeoPlum() {
        return false;
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public int getLevel() {
        return 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(WATERLOGGED));
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.getItemInHand().is(this.asItem())
                || super.canBeReplaced(pState, pUseContext);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    class SpreaderConfig extends MultifaceSpreader.DefaultSpreaderConfig {
        private final MultifaceSpreader.SpreadType[] spreadTypes;
        public SpreaderConfig(MultifaceSpreader.SpreadType... pSpreadTypes) {
            super(RedPlumVein.this);
            this.spreadTypes = pSpreadTypes;
        }

        public boolean stateCanBeReplaced(BlockGetter pLevel, BlockPos pPos, BlockPos pSpreadPos, Direction pDirection,
                                          BlockState pState) {
            BlockState blockstate = pLevel.getBlockState(pSpreadPos.relative(pDirection));
            if (!blockstate.is(BoTags.RED_PLUM_BLOCKS) && !blockstate.is(Blocks.MOVING_PISTON)) {
                if (pPos.distManhattan(pSpreadPos) == 2) {
                    BlockPos blockpos = pPos.relative(pDirection.getOpposite());
                    if (pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, pDirection)) {
                        return false;
                    }
                }
                FluidState fluidstate = pState.getFluidState();
                if (!fluidstate.isEmpty() && !fluidstate.is(Fluids.WATER)) {
                    return false;
                } else if (pState.is(BlockTags.FIRE)) {
                    return false;
                } else {
                    return pState.canBeReplaced() || super.stateCanBeReplaced(pLevel, pPos, pSpreadPos, pDirection,
                            pState);
                }
            } else {
                return false;
            }
        }

        public MultifaceSpreader.SpreadType[] getSpreadTypes() {
            return this.spreadTypes;
        }

        public boolean isOtherBlockValidAsSource(BlockState pOtherBlock) {
            if (RedPlumVein.this.isNeoPlum())
                return !pOtherBlock.is(BlueOceansBlocks.BUDDING_NEO_PLUM.get());
            return !pOtherBlock.is(BlueOceansBlocks.RED_PLUM_VEIN.get());
        }
    }
}
