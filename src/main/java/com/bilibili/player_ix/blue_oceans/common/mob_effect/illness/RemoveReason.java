
package com.bilibili.player_ix.blue_oceans.common.mob_effect.illness;

public enum RemoveReason {
    NATURAL,
    MEDICINE,
    MILK;

    public static RemoveReason of(int pId) {
        switch (pId) {
            case 1 -> {
                return MEDICINE;
            }
            case 2 -> {
                return MILK;
            }
            default -> {
                return NATURAL;
            }
        }
    }
}
