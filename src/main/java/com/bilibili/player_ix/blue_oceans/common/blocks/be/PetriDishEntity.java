
package com.bilibili.player_ix.blue_oceans.common.blocks.be;

import com.bilibili.player_ix.blue_oceans.common.biology.CultivateObject;
//import com.bilibili.player_ix.blue_oceans.network.SyncedLong;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicLong;

public class PetriDishEntity
extends BlockEntity {
    public AtomicLong cultivateTime;
    private CultivateObject cultivateObject = CultivateObject.EMPTY;
    public PetriDishEntity(BlockPos pPos, BlockState pBlockState) {
        super(null, pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, PetriDishEntity pEntity) {
        if (!pEntity.cultivateObject.isEmpty()) {
            pEntity.cultivateTime.addAndGet(1);
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.cultivateObject = CultivateObject.get(pTag.getString("CultivateObject"));
        this.cultivateTime.set(pTag.getLong("CultivateTime"));
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("CultivateObject", this.cultivateObject.name());
        pTag.putLong("CultivateTime", this.cultivateTime.longValue());
    }

    public CultivateObject getCultivateObject() {
        return cultivateObject;
    }
}
