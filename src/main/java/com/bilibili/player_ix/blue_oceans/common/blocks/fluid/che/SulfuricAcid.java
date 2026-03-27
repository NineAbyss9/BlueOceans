
package com.bilibili.player_ix.blue_oceans.common.blocks.fluid.che;

import com.bilibili.player_ix.blue_oceans.common.blocks.fluid.BaseFlowingFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class SulfuricAcid
extends BaseFlowingFluid
{
    public Fluid getFlowing()
    {
        return null;
    }

    public Fluid getSource()
    {
        return null;
    }

    protected boolean canConvertToSource(Level pLevel)
    {
        return true;
    }

    public Item getBucket()
    {
        return null;
    }

    protected boolean canBeReplacedWith(FluidState pState, BlockGetter pLevel, BlockPos pPos, Fluid pFluid, Direction pDirection)
    {
        return false;
    }

    protected float getExplosionResistance()
    {
        return 0;
    }

    protected BlockState createLegacyBlock(FluidState pState)
    {
        return null;
    }

    public boolean isSource(FluidState pState)
    {
        return false;
    }
}
