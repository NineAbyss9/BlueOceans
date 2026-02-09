
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;

public interface IPlumSpreader extends RedPlumMob {
    default boolean shouldAttackOtherMobs() {
        return false;
    }
}
