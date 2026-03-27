
package com.bilibili.player_ix.blue_oceans.common.blocks.fluid.che;

import com.bilibili.player_ix.blue_oceans.common.blocks.fluid.BaseFlowingFluid;
import net.minecraft.world.level.Level;

//TODO fuel
public abstract class Ethanol
extends BaseFlowingFluid
{
    protected boolean canConvertToSource(Level pLevel)
    {
        return true;
    }


}
