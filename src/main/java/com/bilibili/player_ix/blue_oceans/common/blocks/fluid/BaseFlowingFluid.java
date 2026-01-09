
package com.bilibili.player_ix.blue_oceans.common.blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class BaseFlowingFluid
extends FlowingFluid {
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
        pBuilder.add(this.properties());
    }

    protected Property<?>[] properties() {
        return new Property[] {
                LEVEL, FALLING
        };
    }

    protected void beforeDestroyingBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity blockentity = pState.hasBlockEntity() ? pLevel.getBlockEntity(pPos) : null;
        Block.dropResources(pState, pLevel, pPos, blockentity);
    }

    public int getAmount(FluidState pState) {
        return pState.getValue(LEVEL);
    }

    protected int getDropOff(LevelReader pLevel) {
        return 1;
    }

    protected int getSlopeFindDistance(LevelReader pLevel) {
        return 4;
    }

    public int getTickDelay(LevelReader pLevel) {
        return 5;
    }
}
