
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class RedLight
extends RedPlumMonster {
    public RedLight(EntityType<? extends AbstractRedPlumMob> type, Level level) {
        super(type, level);
    }

    public void aiStep() {
        super.aiStep();
    }
}
