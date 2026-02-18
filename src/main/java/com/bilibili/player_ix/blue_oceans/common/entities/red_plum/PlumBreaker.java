
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BoTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.NineAbyss9.util.Action;

import javax.annotation.Nullable;

public class PlumBreaker
extends RedPlumMonster {
    public PlumBreaker(EntityType<? extends PlumBreaker> type, Level level) {
        super(type, level);
    }

    public void aiStep() {
        super.aiStep();

    }

    public static void breakAndConvertBlocks(AABB pBase, Level pLevel, BlockPos pPos,
                                             @Nullable Entity pEntity) {
        var aabb = pBase.inflate(0.3D).move(0, 0.5, 0);
        for (var blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ),
                Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
            BlockState state = pLevel.getBlockState(blockPos);
            if (state.getDestroySpeed(pLevel, pPos) <= 2)
                Action.emptyTrue(() -> spreadPlum(pLevel, blockPos, state)).run(state.is(BoTags.RED_PLUM_BLOCKS));
        }
    }

    public static void spreadPlum(Level pLevel, BlockPos pPos, @SuppressWarnings("unused") BlockState pState) {
        pLevel.setBlockAndUpdate(pPos,//TODO:Replace this
                null);
    }
}
