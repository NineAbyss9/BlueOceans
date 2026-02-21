
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.github.NineAbyss9.ix_api.api.mobs.ApiPoseMob;

public interface IBOMob extends ApiPoseMob {
    ApiPose getArmPose();

    default ApiPose getPoses() {
        return this.getArmPose();
    }
}
