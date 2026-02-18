
package com.bilibili.player_ix.blue_oceans.common.blocks.be;

import com.bilibili.player_ix.blue_oceans.common.biology.CultivateObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PetriDishEntity
extends BlockEntity {
    private long cultivateTime;
    private CultivateObject cultivateObject;
    public PetriDishEntity(BlockPos pPos, BlockState pBlockState) {
        super(null, pPos, pBlockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, PetriDishEntity pEntity) {

    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.cultivateObject = CultivateObject.FINDER.get(pTag.getString("CultivateObject"));
        this.cultivateTime = pTag.getLong("CultivateTime");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("CultivateObject", this.cultivateObject.name());
        pTag.putLong("CultivateTime", this.cultivateTime);
    }

    public CultivateObject getCultivateObject() {
        return cultivateObject;
    }
}
