
package com.bilibili.player_ix.blue_oceans.common.blocks.biology;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public class Microscope
extends Block
{
    public float zoom;
    public Microscope(Properties pProperties, float baseZoom)
    {
        super(pProperties);
        this.zoom = baseZoom;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
}
