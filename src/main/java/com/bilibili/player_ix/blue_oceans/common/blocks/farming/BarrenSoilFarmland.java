
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class BarrenSoilFarmland
extends AbstractFarmland {
    public BarrenSoilFarmland(Properties pProperties) {
        super(pProperties);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pState.canSurvive(pLevel, pPos))
            turnToSoil(null, pState, pLevel, pPos);
    }

    public void turnToSoil(@Nullable Entity pEntity, BlockState pState, Level pLevel, BlockPos pPos) {
        BlockState blockstate = pushEntitiesUp(pState, BlueOceansBlocks.BARREN_SOIL.get()
                .defaultBlockState(), pLevel, pPos);
        pLevel.setBlockAndUpdate(pPos, blockstate);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
    }

    public boolean growCrop(RandomSource pRandom) {
        return false;
    }
}
