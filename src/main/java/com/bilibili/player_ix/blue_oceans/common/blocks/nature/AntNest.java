
package com.bilibili.player_ix.blue_oceans.common.blocks.nature;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.land.Ant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class AntNest
extends Block {
    public static final IntegerProperty ANT_COUNT = IntegerProperty.create("ant_count", 0, 10);
    public AntNest(Properties pProperties) {
        super(pProperties);
    }

    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        int count = pState.getValue(ANT_COUNT);
        if (count > 0) {
            for (int i = 0;i<count;i++) {
                Ant ant = null;
                pLevel.addFreshEntity(ant);
            }
        }
    }
}
