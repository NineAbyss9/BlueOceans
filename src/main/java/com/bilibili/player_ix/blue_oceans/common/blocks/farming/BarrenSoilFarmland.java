
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import net.minecraft.util.RandomSource;

public class BarrenSoilFarmland
extends AbstractFarmland {
    public BarrenSoilFarmland(Properties pProperties) {
        super(pProperties);
    }

    public boolean growCrop(RandomSource pRandom) {
        return false;
    }
}
