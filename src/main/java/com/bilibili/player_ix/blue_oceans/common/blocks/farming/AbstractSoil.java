
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public abstract class AbstractSoil
extends Block {
    public AbstractSoil(Properties pProperties) {
        super(pProperties);
    }

    public abstract Block getFarmland();

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        if (pPlayer.getItemInHand(pHand).is(ItemTags.HOES)) {
            pLevel.playSound(pPlayer, pPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS);
            pLevel.setBlockAndUpdate(pPos, this.getFarmland().defaultBlockState());
        }
        return InteractionResult.PASS;
    }
}
