
package com.bilibili.player_ix.blue_oceans.common.event;

public class WorldEvents {
    public static final WorldEvent PURSUIT;
    public static final WorldEvent EARTHQUAKE;
    private WorldEvents() {
    }

    static {
        PURSUIT = new WorldEvent(WorldEvent.EventType.HARMFUL, 0.002F, "Pursuit");
        EARTHQUAKE = new WorldEvent(WorldEvent.EventType.HARMFUL, 0.0002F,
                "Earthquake");
    }
}
