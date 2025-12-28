
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.government.Government;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public interface ICitizen {
    default Government getGovernment() {
        return Government.EMPTY;
    }

    void setGovernment(Government newGovernment);

    @Nullable
    default LivingEntity getLeader() {
        return this.getGovernment().getLeader();
    }

    default void addCitizenAdditionalSaveData(CompoundTag pTag) {
        pTag.put("Government", this.getGovernment().getAdditionalSaveData());
    }
}
