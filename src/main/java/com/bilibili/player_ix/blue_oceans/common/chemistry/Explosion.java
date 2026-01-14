
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import com.bilibili.player_ix.blue_oceans.client.particles.ImpartParticleOption;
import com.github.player_ix.ix_api.api.annotation.ClientOnly;
import com.github.player_ix.ix_api.util.Vec9;
import net.minecraft.world.level.Level;

public class Explosion {
    private Level level;
    private Vec9 position;
    private double radius;
    public Explosion(Level pLevel, double pRadius) {
        this.level = pLevel;
        this.radius = pRadius * pRadius;
    }

    public Explosion() {
    }

    public Explosion pickLevel(Level pLevel) {
        this.level = pLevel;
        return this;
    }

    public Explosion pickPos(Vec9 pos) {
        this.position = pos;
        return this;
    }

    public Explosion radius(double pRadius) {
        this.radius = pRadius;
        return this;
    }

    public Vec9 position() {
        return this.position == null ? Vec9.of() : position;
    }

    public double x() {
        return position().x();
    }

    public double y() {
        return position().y();
    }

    public double z() {
        return position().y();
    }

    public Level level() {
        return level;
    }

    public double radius() {
        return radius;
    }

    public void trigger() {
        if (this.level.isClientSide)
            this.spawnParticles();
    }

    @ClientOnly
    public void spawnParticles() {
        this.level.addParticle(new ImpartParticleOption(0.5F, (float)radius(), 1.0F), x(), y(), z(), 0, 0, 0);
    }

    public enum Type {
        PHYSICAL,
        CHEMICAL
    }
}
