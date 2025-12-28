
package com.bilibili.player_ix.blue_oceans.api.magic;

public enum BOSpellType {
    NONE(0, 0.0, 0.0, 0.0),
    RANGE(1, 0.7, 0.7, 0.8),
    ATTACK(2, 0.4, 0.3, 0.35),
    NIHILISTIC(3, 0.9, 0.3, 0.9),
    FIRE(4, 1.0, 0.6, 0.0),
    WATER(5, 0.3, 0.3, 0.8),
    DARK(6, 0.01, 0.01, 0.01),
    UNKNOWN(7, 0.3, 0.3, 0.3),
    POTION(8, 0.3, 0.9, 0.3),
    REGEN(9, 0, 0.7, 0.7),
    WATERS(10, 0.1, 0.1, 0.79),
    ZOMBIE(11, 0.3, 0.85, 0.3),
    RED_PLUM(12, 0.3, 0, 0);
    public final int id;
    public final double[] spellColor;

    BOSpellType(int pId, double x, double y, double z) {
        this.id = pId;
        this.spellColor = new double[]{x, y, z};
    }

    public static BOSpellType getById(int nt) {
        for (BOSpellType spellType : BOSpellType.values()) {
            if (nt != spellType.id) continue;
            return spellType;
        }
        return NONE;
    }
}
