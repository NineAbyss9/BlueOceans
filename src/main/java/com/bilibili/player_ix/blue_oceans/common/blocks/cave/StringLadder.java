
package com.bilibili.player_ix.blue_oceans.common.blocks.cave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class StringLadder
extends Block
implements SimpleWaterloggedBlock {
    public static final BooleanProperty TOP;
    public static final BooleanProperty END;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape SHAPE;
    public StringLadder(Properties pProperties) {
        super(pProperties);
        this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(TOP, Boolean.FALSE)
                .setValue(END, Boolean.FALSE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TOP, END, WATERLOGGED);
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return pUseContext.getItemInHand().is(this.asItem());
    }

    public boolean isTop(BlockGetter pLevel, BlockPos pPos) {
        return !(pLevel.getBlockState(pPos.above()).getBlock() instanceof StringLadder);
    }

    public boolean isEnd(BlockGetter pLevel, BlockPos pPos) {
        return !(pLevel.getBlockState(pPos.below()).getBlock() instanceof StringLadder);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext);
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide)
            pLevel.scheduleTick(pPos, this, 1);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockState blockState = setTop(pState, this.isTop(pLevel, pPos)).setValue(END, this.isEnd(pLevel, pPos));
        if (!isTop(pState) && !pLevel.getBlockState(pPos.above()).is(this))
            pLevel.destroyBlock(pPos, true);
        else
            if (pState != blockState)
                pLevel.setBlock(pPos, blockState, 3);
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED))
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        if (!pLevel.isClientSide())
            pLevel.scheduleTick(pPos, this, 1);
        return !canSurvive(pState, pLevel, pPos) ? Blocks.AIR.defaultBlockState() :
                super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(this);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false)
                : Fluids.EMPTY.defaultFluidState();
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        if (pPlayer.isShiftKeyDown()) {
            for (int i = 0; i < 32;) {
                BlockState belowState = pLevel.getBlockState(pPos.below(i + 1));
                if (!belowState.is(this)) {
                    pLevel.destroyBlock(pPos.below(i), false);
                    if (!pPlayer.isCreative())
                        pPlayer.addItem(new ItemStack((ItemLike)null));
                    break;
                } else
                    i++;
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static boolean isTop(BlockState state) {
        return state.getValue(TOP);
    }

    public static BlockState setTop(BlockState state, boolean value) {
        return state.setValue(TOP, value);
    }

    public static boolean isEnd(BlockState state) {
        return state.getValue(END);
    }

    public static BlockState setEnd(BlockState state, boolean value) {
        return state.setValue(END, value);
    }

    static {
        TOP = BooleanProperty.create("top");
        END = BooleanProperty.create("end");
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPE = box(0, 0, 0, 4, 16, 4);
    }
}
