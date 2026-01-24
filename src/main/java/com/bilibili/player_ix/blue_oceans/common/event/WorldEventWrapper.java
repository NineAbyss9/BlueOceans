
package com.bilibili.player_ix.blue_oceans.common.event;

public class WorldEventWrapper
extends WorldEvent {
    private final WorldEvent event;
    public WorldEventWrapper(WorldEvent pEvent) {
        super(pEvent.type, pEvent.happenChance, pEvent.name);
        this.event = pEvent;
    }

    public WorldEvent getEvent() {
        return event;
    }

    public WorldEvent.EventType type() {
        return this.event.type;
    }

    public boolean isBeneficial() {
        return event.isBeneficial();
    }

    public boolean isNeutral() {
        return this.event.isNeutral();
    }

    public boolean isHarmful() {
        return this.event.isHarmful();
    }

    public boolean isSynched() {
        return event.isSynched();
    }

    public void setSynched(boolean pSynched) {
        event.setSynched(pSynched);
    }
}
