
package com.bilibili.player_ix.blue_oceans.common.blocks.nature;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.land.Ant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class AntNest
extends Block
{
    public static final IntegerProperty ANT_COUNT = IntegerProperty.create("ant_count", 0, 10);
    public AntNest(Properties pProperties)
    {
        super(pProperties);
    }

    public void join(Level pLevel, BlockPos pPos, BlockState pState)
    {
        if (!isMaxCount(pState)) {
            pLevel.setBlock(pPos, pState.setValue(ANT_COUNT, pState.getValue(ANT_COUNT) + 1), 3);
        }
    }

    public boolean isMaxCount(BlockState pState) {
        return pState.getValue(ANT_COUNT) > 9;
    }

    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState,
                              @Nullable BlockEntity pBlockEntity, ItemStack pTool)
    {
        int count = pState.getValue(ANT_COUNT);
        if (count > 0) {
            for (int i = 0;i < count;i++) {
                Ant ant = null;
                if (ant != null) {
                    ant.moveTo(pPos, 0, 0);
                    ant.setTarget(pPlayer);
                    pLevel.addFreshEntity(ant);
                }
            }
        }
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
    }
}
