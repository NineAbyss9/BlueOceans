
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.world.level.block.Block;

public abstract class AbstractLog
extends Block {
    public AbstractLog(Properties pProperties) {
        super(pProperties);
    }

    public AbstractLog() {
        this(Properties.of().strength(2.F, 20F));
    }
}
