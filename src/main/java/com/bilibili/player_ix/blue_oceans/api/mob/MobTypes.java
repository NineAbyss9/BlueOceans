
package com.bilibili.player_ix.blue_oceans.api.mob;

public enum MobTypes {
    HOSTILE,
    NEUTRAL,
    FRIENDLY;

    public boolean isHostile() {
        return this.equals(HOSTILE);
    }

    public boolean isNeutral() {
        return this.equals(NEUTRAL);
    }

    public boolean isFriendly() {
        return this.equals(FRIENDLY);
    }
}
