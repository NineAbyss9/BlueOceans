
package com.bilibili.player_ix.blue_oceans.api.mob;

public enum CompletelyPerverseState {
    EGG(0),
    LARVA(1),
    PUPA(2),
    ADULT(3);
    public final int id;
    CompletelyPerverseState(int pId) {
        this.id = pId;
    }

    public boolean isEgg() {
        return this.equals(EGG);
    }

    public boolean isLarva() {
        return this.equals(LARVA);
    }

    public boolean isPupa() {
        return this.equals(PUPA);
    }

    public boolean isAdult() {
        return this.equals(ADULT);
    }

    public static CompletelyPerverseState of(int pId) {
        switch (pId) {
            case 0 -> {
                return CompletelyPerverseState.EGG;
            }
            case 1 -> {
                return CompletelyPerverseState.LARVA;
            }
            case 2 -> {
                return CompletelyPerverseState.PUPA;
            }
            default -> {
                return CompletelyPerverseState.ADULT;
            }
        }
    }
}
