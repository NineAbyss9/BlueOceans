
package com.bilibili.player_ix.blue_oceans.common.event;

import net.minecraft.server.level.ServerLevel;

public class WorldEventRunner {
    private final ServerLevel level;
    public WorldEventRunner(ServerLevel pLevel) {
        this.level = pLevel;
    }

    public void tick() {
    }

    private void selectEvent() {

    }

    public ServerLevel level() {
        return level;
    }

    public static WorldEventRunner getInstance(ServerLevel pLevel) {
        return new WorldEventRunner(pLevel);
    }
}
