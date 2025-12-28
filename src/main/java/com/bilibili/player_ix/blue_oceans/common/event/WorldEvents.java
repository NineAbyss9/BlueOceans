
package com.bilibili.player_ix.blue_oceans.common.event;

public class WorldEvents {
    public static final WorldEvent PURSUIT;
    private WorldEvents() {
    }

    static {
        PURSUIT = new WorldEvent(WorldEvent.EventType.HARMFUL, 0.002F, "Pursuit");
    }
}
