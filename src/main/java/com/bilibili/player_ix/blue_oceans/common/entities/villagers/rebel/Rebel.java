
package com.bilibili.player_ix.blue_oceans.common.entities.villagers.rebel;

import com.github.NineAbyss9.ix_api.api.mobs.ApiPathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Rebel
extends ApiPathfinderMob {
    public Rebel(EntityType<? extends Rebel> type, Level level) {
        super(type, level);
    }

    protected void registerGoals() {
        super.registerGoals();
    }
}
