
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import net.minecraft.world.level.Level;

public class Explosion {
    private Level level;
    public double radius;
    public Explosion(Level pLevel, double pRadius) {
        this.level = pLevel;
        this.radius = pRadius * pRadius;
    }

    public Level level() {
        return level;
    }

    public double getRadius() {
        return radius;
    }

    public void trigger() {
    }

    public enum Type {
        PHYSICAL,
        CHEMICAL
    }
}
