
package com.bilibili.player_ix.blue_oceans.common.blocks.fluid;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public abstract class NuclearContaminatedWater
extends FlowingFluid {
    public NuclearContaminatedWater() {
        super();
    }

    protected boolean canConvertToSource(Level pLevel) {
        return true;
    }

    protected void beforeDestroyingBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
    }

    public Fluid getFlowing() {
        return BlueOceansFluids.NCW_FLOWING.get();
    }

    public Fluid getSource() {
        return BlueOceansFluids.NUCLEAR_CONTAMINATED_WATER.get();
    }

    protected int getSlopeFindDistance(LevelReader pLevel) {
        return 0;
    }

    protected int getDropOff(LevelReader pLevel) {
        return 1;
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
        return 0;
    }

    protected BlockState createLegacyBlock(FluidState pState) {
        return pState.createLegacyBlock();
    }

    public boolean isSource(FluidState pState) {
        return false;
    }

    public int getAmount(FluidState pState) {
        return 0;
    }

    public static class Source extends NuclearContaminatedWater {
        public boolean isSource(FluidState pState) {
            return true;
        }
    }

    public static class Flowing extends NuclearContaminatedWater {
    }
}
