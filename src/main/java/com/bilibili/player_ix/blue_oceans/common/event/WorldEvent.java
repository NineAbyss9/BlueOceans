
package com.bilibili.player_ix.blue_oceans.common.event;

/**@author Player_IX*/
public class WorldEvent {
    /**The {@linkplain WorldEvent.EventType} of a {@linkplain WorldEvent}*/
    public final EventType type;
    public final float happenChance;
    public final String name;
    private boolean synched;
    public WorldEvent(EventType pType, float pHappenChance, String pName) {
        type = pType;
        happenChance = pHappenChance;
        name = pName == null ? (pType + "WorldEvent") : pName;
    }

    public WorldEvent(EventType pType, float pHappenChance) {
        this(pType, pHappenChance, null);
    }

    public boolean isBeneficial() {
        return this.type.isBeneficial();
    }

    public boolean isNeutral() {
        return this.type.isNeutral();
    }

    public boolean isHarmful() {
        return this.type.isHarmful();
    }

    public boolean isSynched() {
        return synched;
    }

    public void setSynched(boolean pSynched) {
        this.synched = pSynched;
    }

    public enum EventType {
        BENEFICIAL(0),
        NEUTRAL(1),
        HARMFUL(2);
        public final int level;
        EventType(int pLevel) {
            level = pLevel;
        }

        public boolean isBeneficial() {
            return this.level == 0;
        }

        public boolean isNeutral() {
            return this.level == 1;
        }

        public boolean isHarmful() {
            return this.level == 2;
        }

        public int getLevel() {
            return level;
        }

        public static EventType of(int pId) {
            switch (pId) {
                case 0 -> {
                    return BENEFICIAL;
                }
                case 1 -> {
                    return NEUTRAL;
                }
                default -> {
                    return HARMFUL;
                }
            }
        }
    }
}
