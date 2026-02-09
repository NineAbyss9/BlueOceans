
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.world.level.block.RotatedPillarBlock;

public abstract class AbstractLog
extends RotatedPillarBlock {
    public AbstractLog(Properties pProperties) {
        super(pProperties);
    }

    public AbstractLog() {
        this(Properties.of().strength(2.F, 20F));
    }
}
