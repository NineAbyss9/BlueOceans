
package com.bilibili.player_ix.blue_oceans.common.blocks.food;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;
import org.NineAbyss9.array.ObjectArray;

public class RiceBlock
extends Crop
implements LiquidBlockContainer {
    static final ObjectArray<VoxelShape> SHAPE_BY_AGE = ObjectArray.of(
            box(1.0D, 0.0D, 1.0D, 13.0D, 8.0D, 13.0D),
            box(2.0D, 0.0D, 2.0D, 13.0D, 10.0D, 13.0D),
            box(3.0D, 0.0D, 3.0D, 14.0D, 12.0D, 14.0D),
            box(3.0D, 0.0D, 3.0D, 15.0D, 16.0D, 15.0D));
    public RiceBlock(Properties pProperties) {
        super(pProperties, 3);
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1))
            return;
        BlockPos above = pPos.above();
        if (pLevel.getRawBrightness(above, 0) >= 6) {
            int currentAge = this.getAge(pState);
            if (currentAge <= this.getMaxAge()) {
                if (ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int)(25.0F / 10) + 1) == 0)) {
                    if (currentAge == this.getMaxAge()) {
                        RiceEars ears = BlueOceansBlocks.RICE_EARS.get();
                        if (ears.defaultBlockState().canSurvive(pLevel, above) && pLevel.isEmptyBlock(above)) {
                            pLevel.setBlockAndUpdate(above, ears.defaultBlockState());
                            ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                        }
                    } else {
                        pLevel.setBlock(pPos, this.defaultBlockState().setValue(
                                this.getAgeProperty(), currentAge + 1), 2);
                        ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                    }
                }
            }
        }
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (!pLevel.getBlockState(pPos.above()).is(Blocks.WATER))
            return false;
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(Blocks.DIRT);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluid = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return fluid.is(FluidTags.WATER) ? super.getStateForPlacement(pContext) : null;
    }

    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState pState) {
        return Fluids.WATER.getSource(false);
    }

    protected ItemLike getBaseSeedId() {
        return BlueOceansItems.RICE_SEEDS.get();
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE.get(//AbyssMath.clampI(
                this.getAge(pState)
                //, 0, 4)
        );
    }

    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        BlockState state = pLevel.getBlockState(pPos.above());
        if (state.getBlock() instanceof RiceEars ears) {
            return !ears.isMaxAge(state);
        }
        return super.isValidBonemealTarget(pLevel, pPos, pState, pIsClient)
                || state.isAir();
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        //int i = Math.min(this.getAge(pState) + this.getBonemealAgeIncrease(pLevel), this.getMaxAge());
        if (this.isMaxAge(pState)) {
            BlockPos above = pPos.above();
            BlockState state = pLevel.getBlockState(above);
            if (state.is(BlueOceansBlocks.RICE_EARS.get())) {
                BonemealableBlock block = (BonemealableBlock)state.getBlock();
                if (block.isValidBonemealTarget(pLevel, above, state, false))
                    block.performBonemeal(pLevel, pRandom, pPos, state);
            } else {
                Block ears = BlueOceansBlocks.RICE_EARS.get();
                if (ears.defaultBlockState().canSurvive(pLevel, above) && pLevel.isEmptyBlock(above)) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(this.getAgeProperty(), this.getMaxAge()));
                    pLevel.setBlock(above, ears.defaultBlockState()//.setValue(RiceEars.EARS_AGE, 0)
                            , 2);
                }
            }
        }
        super.performBonemeal(pLevel, pRandom, pPos, pState);
    }

    public int getMaxAge() {
        return 3;
    }

    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return false;
    }

    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }
}
