
package com.bilibili.player_ix.blue_oceans.common.blocks.cave;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.github.NineAbyss9.ix_api.util.ItemUtil;
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

/**Based on
 *<a href="https://github.com/AstralOrdana/Spelunkery/blob/1.20.1/common/src/main/java/com/ordana/spelunkery/blocks/RopeLadderBlock.java">...</a>*/
@SuppressWarnings("deprecation")
public class Rope
extends Block
implements SimpleWaterloggedBlock {
    public static final BooleanProperty TOP;
    public static final BooleanProperty END;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape SHAPE;
    public Rope(Properties pProperties) {
        super(pProperties);
        this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(TOP, Boolean.FALSE)
                .setValue(END, Boolean.FALSE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TOP, END, WATERLOGGED);
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return pUseContext.getItemInHand().is(BlueOceansItems.ROPE.get());
    }

    public static boolean isTop(BlockGetter pLevel, BlockPos pPos) {
        return !(pLevel.getBlockState(pPos.above()).getBlock() instanceof Rope);
    }

    public static boolean isEnd(BlockGetter pLevel, BlockPos pPos) {
        return !(pLevel.getBlockState(pPos.below()).getBlock() instanceof Rope);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        return state(level, pos);
    }

    @Nullable
    public static BlockState state(Level pLevel, BlockPos pos) {
        BlockState blockState = BlueOceansBlocks.ROPE.get().defaultBlockState().setValue(TOP,
                        isTop(pLevel, pos)).setValue(END, isEnd(pLevel, pos))
                .setValue(WATERLOGGED, pLevel.getFluidState(pos).is(Fluids.WATER));
        BlockState above = pLevel.getBlockState(pos.above());
        if (blockState.getValue(TOP))
            return blockState;
        else if (above.getBlock() instanceof Rope)
            return blockState;
        return null;
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide)
            pLevel.scheduleTick(pPos, this, 1);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockState blockState = pState.setValue(TOP, isTop(pLevel, pPos)).setValue(END, isEnd(pLevel, pPos));
        if (!pState.getValue(TOP) && !pLevel.getBlockState(pPos.above()).is(this))
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
        return //!canSurvive(pState, pLevel, pPos) ? Blocks.AIR.defaultBlockState() :
                super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false)
                : Fluids.EMPTY.defaultFluidState();
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (pPlayer.isCrouching()) {
            if (isTop(pLevel, pPos)) {
                int i = 0;
                while (i < 39) {
                    BlockState belowState = pLevel.getBlockState(pPos.above(i + 1));
                    if (!belowState.is(this)) {
                        pLevel.destroyBlock(pPos.above(i), false);
                        if (!ItemUtil.instabuild(pPlayer))
                            pPlayer.addItem(new ItemStack(BlueOceansItems.ROPE.get()));
                        break;
                    } else
                        i++;
                }
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            } else if (isEnd(pLevel, pPos)) {
                for (int i = 0;i < 39;) {
                    BlockState belowState = pLevel.getBlockState(pPos.above(i + 1));
                    if (!belowState.is(this)) {
                        pLevel.destroyBlock(pPos.above(i), false);
                        if (!ItemUtil.instabuild(pPlayer))
                            pPlayer.addItem(new ItemStack(BlueOceansItems.ROPE.get()));
                        break;
                    } else
                        i++;
                }
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        } else if (stack.is(BlueOceansItems.ROPE.get())){
            int i = 0;
            while (i < 39) {
                i++;
                BlockPos below = pPos.below(i);
                BlockState blockState = state(pLevel, pPos);
                if (pLevel.getBlockState(below).canBeReplaced()) {
                    if (blockState != null) {
                        pLevel.setBlock(below, blockState, 0);
                        ItemUtil.shrink(stack, pPlayer);
                        return InteractionResult.sidedSuccess(pLevel.isClientSide);
                    }
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    static {
        TOP = BoBlockProperties.TOP;
        END = BoBlockProperties.END;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPE = box(6, 0, 6, 10, 16, 10);
    }
}
