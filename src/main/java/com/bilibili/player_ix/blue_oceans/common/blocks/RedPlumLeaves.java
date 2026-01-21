
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public class RedPlumLeaves
extends Block
implements IPlumBlock {
    public RedPlumLeaves() {
        super(Properties.of().strength(20F, 10F).mapColor(DyeColor.RED));
    }
}
