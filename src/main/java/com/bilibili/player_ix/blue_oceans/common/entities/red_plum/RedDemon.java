
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class RedDemon
extends RedPlumMonster {
    public RedDemon(EntityType<? extends RedDemon> type, Level level) {
        super(type, level);
    }

    public void aiStep() {
        super.aiStep();
    }
}
