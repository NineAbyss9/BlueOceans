
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import net.minecraft.world.level.block.Block;

public abstract class AbstractBomb
extends Block {
    public AbstractBomb(Properties pProperties) {
        super(pProperties);
    }

    public abstract float explodePower();
}
