
package com.bilibili.player_ix.blue_oceans.common.blocks.corpse;

import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PlumCorpse
extends Corpse
{
    public PlumCorpse(Properties pP)
    {
        super(pP);
    }

    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity)
    {
        if (pEntity.tickCount % 10 == 0 && Math.random() < 0.3d && pEntity instanceof LivingEntity entity) {
            if (Math.random() < 0.1d)
                entity.addEffect(RedPlumUtil.plumInfection(600, 1));
            else
                entity.addEffect(RedPlumUtil.plumInfection());
        }
    }
}
