
package com.bilibili.player_ix.blue_oceans.common.item.plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.world.item.BlockItem;

public class PlumCellCluster
extends BlockItem {
    public PlumCellCluster() {
        super(BlueOceansBlocks.PLUM_CELL_CLUSTER.get(), new Properties().stacksTo(64));
    }
}
