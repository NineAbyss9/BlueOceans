
package com.bilibili.player_ix.blue_oceans.api.mob;

public enum HemimetabolousState {
    EGG(0),
    NYMPH(1),
    ADULT(2);
    public final int id;
    HemimetabolousState(int pId) {
        id = pId;
    }

    public static HemimetabolousState of(int pId) {
        switch (pId) {
            case 0 -> {
                return EGG;
            }
            case 1 -> {
                return NYMPH;
            }
            default -> {
                return ADULT;
            }
        }
    }

    public static interface Interface {
        void setHemState(HemimetabolousState pState);

        HemimetabolousState getHemState();
    }
}
