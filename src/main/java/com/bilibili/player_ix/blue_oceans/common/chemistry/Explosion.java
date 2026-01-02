
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import com.bilibili.player_ix.blue_oceans.client.particles.ImpartParticleOption;
import com.bilibili.player_ix.blue_oceans.network.BoNetwork;
import com.bilibili.player_ix.blue_oceans.network.packet.ParticlePacket;
import com.github.player_ix.ix_api.util.Vec9;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.network.Connection;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;

public class Explosion {
    private Level level;
    private Vec9 position;
    private double radius;
    private Connection connection;
    public Explosion(Level pLevel, double pRadius) {
        this.level = pLevel;
        this.radius = pRadius * pRadius;
    }

    public Explosion(Level pLevel, double pRadius, Connection pConnection) {
        this(pLevel, pRadius);
        this.pickConnection(pConnection);
    }

    @CanIgnoreReturnValue
    public Explosion pickLevel(Level pLevel) {
        this.level = pLevel;
        return this;
    }

    @CanIgnoreReturnValue
    public Explosion pickConnection(Connection pConnection) {
        this.connection = pConnection;
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
        this.spawnParticles();
    }

    public void spawnParticles() {
        BoNetwork.INSTANCE.sendTo(new ParticlePacket(new ImpartParticleOption(0.1F, 0.5F),
                this.position(), 0, 0, 0), connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public enum Type {
        PHYSICAL,
        CHEMICAL
    }
}
