
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public abstract class AbstractBomb
extends Block {
    public AbstractBomb(Properties pProperties) {
        super(pProperties);
    }

    public void explode(Level pLevel, BlockPos pPos) {
        pLevel.explode(null, pPos.getX(), pPos.getY(), pPos.getZ(), explodePower(), true,
                Level.ExplosionInteraction.BLOCK);
    }

    public abstract float explodePower();
}
