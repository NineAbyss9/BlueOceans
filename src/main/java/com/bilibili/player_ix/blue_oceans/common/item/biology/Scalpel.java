
package com.bilibili.player_ix.blue_oceans.common.item.biology;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class Scalpel
extends SwordItem
{
    public Scalpel()
    {
        super(BoTier.IRON, 3, -2, new Properties());
    }

    public InteractionResult useOn(UseOnContext pContext)
    {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        level.destroyBlock(pos, false, pContext.getPlayer());
        return super.useOn(pContext);
    }
}
