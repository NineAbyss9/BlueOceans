
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.water;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public abstract class AquaticPlant
extends CropBlock
implements IForgeShearable, LiquidBlockContainer, BonemealableBlock
{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    private ToListFunc sharedDrops = (player, item, level, pos, fortune) ->
            Collections.emptyList();
    public AquaticPlant(Properties pProperties)
    {
        super(pProperties);
    }

    public AquaticPlant(Properties pP, ToListFunc pDrops)
    {
        this(pP);
        this.sharedDrops = pDrops;
    }

    public boolean canGrow()
    {
        return false;
    }

    public boolean isShearable(ItemStack item, Level level, BlockPos pos)
    {
        return false;
    }

    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos, int fortune)
    {
        if (this.isShearable(item, level, pos))
            return this.sharedDrops.toList(player, item, level, pos, fortune);
        else
            return Collections.emptyList();
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos)
    {
        return pState.isFaceSturdy(pLevel, pPos, Direction.UP) && !pState.is(Blocks.MAGMA_BLOCK);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(pContext) : null;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos,
                                  BlockPos pFacingPos)
    {
        BlockState blockstate = super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        if (!blockstate.isAir()) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return blockstate;
    }

    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient)
    {
        return true;
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState)
    {
        return true;
    }

    public FluidState getFluidState(BlockState pState)
    {
        return Fluids.WATER.getSource(false);
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState)
    {
        spread(defaultBlockState(), pLevel, pRandom, pPos, pState);
    }

    /**@param state the state will be placed
     *
     * @param pState the origin state
     *
     * @param pChance the spread chance*/
    public static void spread(BlockState state, ServerLevel pLevel, RandomSource pRandom, BlockPos pPos,
                              BlockState pState, double pChance)
    {
        for (BlockPos pos1 : BlockPos.betweenClosed(pPos.offset(-2, 0, -2), pPos.offset(2, 0, 2))) {
            if (pLevel.getBlockState(pos1).isAir() && state.canSurvive(pLevel, pos1) && Math.random() < pChance) {
                pLevel.setBlock(pos1, state, 2);
            }
        }
    }

    public static void spread(BlockState state, ServerLevel pLevel, RandomSource pRandom, BlockPos pPos,
                              BlockState pState)
    {
        spread(state, pLevel, pRandom, pPos, pState, 0.05d);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (canGrow()) {
            super.randomTick(pState, pLevel, pPos, pRandom);
        }
    }

    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid)
    {
        return false;
    }

    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState)
    {
        return false;
    }

    public interface ToListFunc
    {
        List<ItemStack> toList(@Nullable Player player, ItemStack item, Level level, BlockPos pos, int fortune);
    }
}
