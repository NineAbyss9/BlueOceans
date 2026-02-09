
package com.bilibili.player_ix.blue_oceans.common.event;

import net.minecraft.server.level.ServerLevel;

public class WorldEventRunner {
    private WorldEventWrapper currentEvent;
    public void tick(ServerLevel pLevel) {
        this.selectEvent(pLevel);
    }

    private void selectEvent(ServerLevel pLevel) {
        currentEvent = new WorldEventWrapper(WorldEvents.EARTHQUAKE);
    }
}
