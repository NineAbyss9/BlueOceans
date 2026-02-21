
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class BlackSoilFarmland
extends FarmBlock {
    public BlackSoilFarmland(Properties pProperties) {
        super(pProperties);
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (!pLevel.isClientSide
                && pFallDistance > 1F
                && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(pLevel, pPos,
                BlueOceansBlocks.BLACK_SOIL.get().defaultBlockState(), pFallDistance, pEntity))
            turnToBlackSoil(pEntity, pState, pLevel, pPos);
        pEntity.causeFallDamage(pFallDistance, 1.0F, pLevel.damageSources().fall());
    }

    public static void turnToBlackSoil(@Nullable Entity pEntity, BlockState pState, Level pLevel, BlockPos pPos) {
        BlockState blockstate = pushEntitiesUp(pState, BlueOceansBlocks.BLACK_SOIL.get().defaultBlockState(), pLevel, pPos);
        pLevel.setBlockAndUpdate(pPos, blockstate);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
    }
}
