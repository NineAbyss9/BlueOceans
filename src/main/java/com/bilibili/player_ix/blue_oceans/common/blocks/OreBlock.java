
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.DropExperienceBlock;

public class OreBlock
extends DropExperienceBlock {
    public OreBlock(Properties pProperties) {
        super(pProperties);
    }

    public OreBlock(Properties pProperties, IntProvider pXpRange) {
        super(pProperties, pXpRange);
    }
}
