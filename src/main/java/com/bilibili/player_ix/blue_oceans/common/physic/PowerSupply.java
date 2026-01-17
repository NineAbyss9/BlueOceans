
package com.bilibili.player_ix.blue_oceans.common.physic;

public class PowerSupply implements Electric {
    private boolean isShortCircuited;
    private float volt;
    private float a;
    public PowerSupply() {
    }

    public boolean isShortCircuited() {
        return isShortCircuited;
    }

    public float getVolt() {
        return volt;
    }

    public float getA() {
        return a;
    }
}
