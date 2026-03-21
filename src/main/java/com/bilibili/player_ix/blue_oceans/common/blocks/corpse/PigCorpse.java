
package com.bilibili.player_ix.blue_oceans.common.blocks.corpse;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.CorpseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PigCorpse
extends Corpse
{
    public PigCorpse(Properties pProperties)
    {
        super(pProperties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        var e = new CorpseEntity(pPos, pState);
        e.setEntity(EntityType.PIG);
        return e;
    }
}
