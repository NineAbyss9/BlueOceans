
package com.bilibili.player_ix.blue_oceans.network.packet;

import com.bilibili.player_ix.blue_oceans.client.SideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.nine_abyss.annotation.NotCheckUnused;

import java.util.Objects;
import java.util.function.Supplier;

public record ParticlePacket(ParticleOptions options, double x, double y, double z,
                             double dx, double dy, double dz) {
    @SuppressWarnings("deprecation")
    public static <T extends ParticleOptions> void writeParticle(T options, FriendlyByteBuf buffer) {
        buffer.writeInt(BuiltInRegistries.PARTICLE_TYPE.getId(options.getType()));
    }

    @NotCheckUnused
    public static void encode(ParticlePacket packet, FriendlyByteBuf buffer) {
        writeParticle(packet.options(), buffer);
    }

    @NotCheckUnused
    public static ParticlePacket decode(ParticleOptions options, double x, double y, double z,
                                        double dx, double dy, double dz) {
        return new ParticlePacket(options, x, y, z, dx, dy, dz);
    }

    @NotCheckUnused
    public static void handle(ParticlePacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context=ctx.get();
        context.enqueueWork(() -> {
            if (SideHandler.isServerSide())
                return;
            //TryBlock block = new TryBlock(() ->
            if (SideHandler.checkLevel())
                Objects.requireNonNull(Minecraft.getInstance().level).addParticle(packet.options(), packet.x(), packet.y(),
                        packet.z(), packet.dx(), packet.dy(), packet.dz());
            //block.run();
        });
        context.setPacketHandled(true);
    }
}
