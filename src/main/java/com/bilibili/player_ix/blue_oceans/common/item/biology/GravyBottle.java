
package com.bilibili.player_ix.blue_oceans.common.item.biology;

import com.github.NineAbyss9.ix_api.api.item.BaseItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GravyBottle extends BaseItem
{
    public GravyBottle()
    {
        super(16);
    }

    public InteractionResult useOn(UseOnContext pContext)
    {
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState state = level.getBlockState(blockPos);
        return super.useOn(pContext);
    }
}
