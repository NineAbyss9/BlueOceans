
package com.bilibili.player_ix.blue_oceans.common.blocks.fluid;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public abstract class NuclearContaminatedWater
extends BaseFlowingFluid {
    private NuclearContaminatedWater() {
        super();
    }

    protected boolean canConvertToSource(Level pLevel) {
        return true;
    }

    protected boolean canSpreadTo(BlockGetter pLevel, BlockPos pFromPos, BlockState pFromBlockState,
                                  Direction pDirection, BlockPos pToPos, BlockState pToBlockState, FluidState pToFluidState, Fluid pFluid) {
        return pToBlockState.isAir() && super.canSpreadTo(pLevel, pFromPos, pFromBlockState, pDirection, pToPos,
                pToBlockState, pToFluidState, pFluid);
    }

    public Fluid getFlowing() {
        return BlueOceansFluids.NCW_FLOWING.get();
    }

    public Fluid getSource() {
        return BlueOceansFluids.NUCLEAR_CONTAMINATED_WATER.get();
    }

    public Item getBucket() {
        return Items.BUCKET;
    }

    protected boolean canBeReplacedWith(FluidState pState, BlockGetter pLevel, BlockPos pPos, Fluid pFluid,
                                        Direction pDirection) {
        return pState.is(Fluids.LAVA);
    }

    public int getTickDelay(LevelReader pLevel) {
        return 10;
    }

    protected float getExplosionResistance() {
        return 25F;
    }

    protected BlockState createLegacyBlock(FluidState pState) {
        return pState.createLegacyBlock();
    }

    public boolean isSource(FluidState pState) {
        return false;
    }

    public static class Source extends NuclearContaminatedWater {
        public boolean isSource(FluidState pState) {
            return true;
        }

        protected Property<?>[] properties() {
            return new Property[] {FALLING};
        }

        public int getAmount(FluidState pState) {
            return 8;
        }
    }

    public static class Flowing extends NuclearContaminatedWater {
    }
}
