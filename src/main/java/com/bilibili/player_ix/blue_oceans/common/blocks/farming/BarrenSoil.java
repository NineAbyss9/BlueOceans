
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.world.level.block.Block;

public class BarrenSoil
extends AbstractSoil {
    public BarrenSoil(Properties pProperties) {
        super(pProperties);
    }

    public Block getFarmland() {
        return BlueOceansBlocks.BARREN_SOIL_FARMLAND.get();
    }
}
