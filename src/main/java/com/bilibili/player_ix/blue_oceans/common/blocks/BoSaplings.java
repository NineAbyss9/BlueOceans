
package com.bilibili.player_ix.blue_oceans.common.blocks;

import com.bilibili.player_ix.blue_oceans.world.features.tree.OrangeTreeGrower;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public class BoSaplings {
    public static AbstractTreeGrower orange() {
        return new OrangeTreeGrower();
    }

}
