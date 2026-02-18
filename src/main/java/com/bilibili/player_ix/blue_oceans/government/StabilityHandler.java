
package com.bilibili.player_ix.blue_oceans.government;

import org.NineAbyss9.math.AbyssMath;

public class StabilityHandler {
    private float stability;
    public StabilityHandler(float pStabilityBase) {
        this();
        this.stability = pStabilityBase;
    }

    public StabilityHandler() {
        stability = 0.0F;
    }

    public float getStability() {
        return stability;
    }

    public void setStability(float pStability) {
        this.stability = AbyssMath.clamp(pStability, 0.0F, 100.0F);
    }

    public void setMin() {
        this.setStability(0.0F);
    }

    public void setMax() {
        this.setStability(100F);
    }

    public void increase(float pStability) {
        this.setStability(this.getStability() + pStability);
    }

    public void reduce(float pStability) {
        this.setStability(this.getStability() - pStability);
    }
}
