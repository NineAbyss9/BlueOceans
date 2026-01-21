
package com.bilibili.player_ix.blue_oceans.common.blocks;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
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

@SuppressWarnings("deprecation")
public class RedPlumVein
extends MultifaceBlock
implements SimpleWaterloggedBlock, IPlumBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final MultifaceSpreader veinSpreader =
            new MultifaceSpreader(new SpreaderConfig(MultifaceSpreader.DEFAULT_SPREAD_ORDER));
    //private final MultifaceSpreader sameSpaceSpreader =
    //        new MultifaceSpreader(new SpreaderConfig(MultifaceSpreader.SpreadType.SAME_POSITION));
    public RedPlumVein(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE));
    }

    public MultifaceSpreader getSpreader() {
        return this.veinSpreader;
    }

    public BlockState getSupportBlock(Level pLevel, BlockPos pVeinPos) {
        return pLevel.getBlockState(pVeinPos.relative(this.getFacing(pLevel.getBlockState(pVeinPos))));
    }

    public Direction getFacing(BlockState pState) {
        for (Direction direction : DIRECTIONS) {
            if (hasFace(pState, direction))
                return direction;
        }
        return Direction.UP;
    }

    /*public void onDischarged(LevelAccessor pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom) {
        if (pState.is(this)) {
            for (Direction direction : DIRECTIONS) {
                BooleanProperty booleanproperty = getFaceProperty(direction);
                if (pState.getValue(booleanproperty) && pLevel.getBlockState(pPos.relative(direction))
                        .is(BlueOceansBlocks.RED_PLUM_BLOCK.get())) {
                    pState = pState.setValue(booleanproperty, Boolean.valueOf(false));
                }
            }
            if (!hasAnyFace(pState)) {
                FluidState fluidstate = pLevel.getFluidState(pPos);
                pState = (fluidstate.isEmpty() ? Blocks.AIR : Blocks.WATER).defaultBlockState();
            }
            pLevel.setBlock(pPos, pState, 3);
            SculkBehaviour.super.onDischarged(pLevel, pState, pPos, pRandom);
        }
    }

    public int attemptUseCharge(SculkSpreader.ChargeCursor pCursor, LevelAccessor pLevel, BlockPos pPos,
                                RandomSource pRandom, SculkSpreader pSpreader, boolean pShouldConvertBlocks) {
        if (pShouldConvertBlocks && this.attemptPlaceSculk(pSpreader, pLevel, pCursor.getPos(), pRandom)) {
            return pCursor.getCharge() - 1;
        } else {
            return pRandom.nextInt(pSpreader.chargeDecayRate()) == 0 ? Mth.floor((float)pCursor.getCharge()
                    * 0.5F) : pCursor.getCharge();
        }
    }

    private boolean attemptPlaceSculk(SculkSpreader pSpreader, LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        TagKey<Block> key = pSpreader.replaceableBlocks();
        for (Direction direction : Direction.allShuffled(pRandom)) {
            if (hasFace(blockstate, direction)) {
                BlockPos blockpos = pPos.relative(direction);
                BlockState blockstate1 = pLevel.getBlockState(blockpos);
                if (blockstate1.is(key)) {
                    BlockState blockstate2 = Blocks.SCULK.defaultBlockState();
                    pLevel.setBlock(blockpos, blockstate2, 3);
                    Block.pushEntitiesUp(blockstate1, blockstate2, pLevel, blockpos);
                    pLevel.playSound(null, blockpos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS,
                            1.0F, 1.0F);
                    this.veinSpreader.spreadAll(blockstate2, pLevel, blockpos, pSpreader.isWorldGeneration());
                    Direction direction1 = direction.getOpposite();
                    for (Direction direction2 : DIRECTIONS) {
                        if (direction2 != direction1) {
                            BlockPos blockpos1 = blockpos.relative(direction2);
                            BlockState blockstate3 = pLevel.getBlockState(blockpos1);
                            if (blockstate3.is(this)) {
                                this.onDischarged(pLevel, blockstate3, blockpos1, pRandom);
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }*/

    public static boolean hasSubstrateAccess(LevelAccessor pLevel, BlockState pState, BlockPos pPos) {
        if (pState.is(Blocks.SCULK_VEIN)) {
            for (Direction direction : DIRECTIONS) {
                if (hasFace(pState, direction) && pLevel.getBlockState(pPos.relative(direction))
                        .is(BlockTags.SCULK_REPLACEABLE)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(10) == 0) {
            BlockPos pos;
            for (int i = 0;i<4;i++) {
                if (i == 0) {
                    pos = pPos.offset(1, 0, 0);
                } else if (i == 1) {
                    pos = pPos.offset(-1, 0, 0);
                } else if (i == 2) {
                    pos = pPos.offset(0, 0, 1);
                } else {
                    pos = pPos.offset(0, 0, -1);
                }
                BlockPos belowPos = pos.below();
                if (!pLevel.getBlockState(pos).is(BoTags.RED_PLUM_BLOCKS) &&
                        Block.isFaceFull(pLevel.getBlockState(belowPos).getBlockSupportShape(pLevel, belowPos),
                                Direction.UP))
                    pLevel.setBlockAndUpdate(pos, BlueOceansBlocks.RED_PLUM_VEIN.get().defaultBlockState());
            }
            BlockPos below = pPos.relative(this.getFacing(pState));
            if (!pLevel.getBlockState(below).is(BoTags.RED_PLUM_BLOCKS)) {
                pLevel.setBlockAndUpdate(below, BlueOceansBlocks.RED_PLUM_BLOCK.get().defaultBlockState());
                if (pLevel.getBlockState(pPos).is(BlueOceansBlocks.RED_PLUM_VEIN.get()))
                    pLevel.destroyBlock(pPos, false);
            }
        }
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
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED);
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.getItemInHand().is(BlueOceansItems.RED_PLUM_VEIN.get())
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
            if (!blockstate.is(BlueOceansBlocks.RED_PLUM_BLOCK.get()) &&
                    !blockstate.is(BlueOceansBlocks.RED_PLUM_CATALYST.get()) &&
                    !blockstate.is(BlueOceansBlocks.RED_PLUM_TRAP.get()) && !blockstate.is(Blocks.MOVING_PISTON)) {
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
            return !pOtherBlock.is(Blocks.SCULK_VEIN);
        }
    }
}
