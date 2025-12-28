
package com.bilibili.player_ix.blue_oceans.common.entities;

import com.bilibili.player_ix.blue_oceans.api.mob.IBOMob;
import com.github.player_ix.ix_api.api.ApiPose;
import com.github.player_ix.ix_api.api.mobs.ApiPathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class AbstractBlueOceansMob
extends ApiPathfinderMob
implements IBOMob {
    protected AbstractBlueOceansMob(EntityType<? extends AbstractBlueOceansMob> type, Level level) {
        super(type, level);
    }

    public ApiPose getArmPose() {
        return ApiPose.NATURAL;
    }
}
