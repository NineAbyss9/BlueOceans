
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.land;

import com.bilibili.player_ix.blue_oceans.common.blocks.nature.PlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class ThornBlock
extends PlantBlock
{
    public ThornBlock(Properties pProperties)
    {
        super(pProperties);
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity)
    {
        pEntity.hurt(pLevel.damageSources().cactus(), 1.5f);
    }
}
