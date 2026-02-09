
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.world.item.DyeColor;

public class RedPlumLog
extends AbstractLog
implements IPlumBlock {
    public RedPlumLog() {
        super(Properties.of().mapColor(DyeColor.RED).strength(60F, 20F));
    }


}
