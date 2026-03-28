
package com.bilibili.player_ix.blue_oceans.common.blocks.food;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public class Chili
extends Crop
{
    public Chili(Properties pProperties)
    {
        super(pProperties, 4);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.isEmpty() || stack.is(this.asItem()))
        {
            popResource(pLevel, pPos, this.asItem().getDefaultInstance());
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
