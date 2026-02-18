
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.common.blocks.AbstractLog;
import net.minecraft.world.item.DyeColor;

public class RedPlumLog
extends AbstractLog
implements PlumBlock {
    public RedPlumLog() {
        super(Properties.of().mapColor(DyeColor.RED).strength(60F, 20F));
    }


}
