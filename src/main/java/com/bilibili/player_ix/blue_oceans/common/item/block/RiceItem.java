
package com.bilibili.player_ix.blue_oceans.common.item.block;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class RiceItem
extends ItemNameBlockItem {
    public RiceItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        return super.useOn(pContext);
    }
}
