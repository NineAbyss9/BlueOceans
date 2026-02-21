
package com.github.NineAbyss9.ix_api.network;

import com.github.NineAbyss9.ix_api.IXApi;
import com.github.NineAbyss9.ix_api.network.packet.BossBarUpdatePacket;
import com.github.NineAbyss9.ix_api.util.ResourceLocations;
import com.google.common.base.Predicates;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class APINetwork {
    private static final String PROTOCOL = "ix_api_packet";
    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(ResourceLocations.fromNamespaceAndPath
                        (IXApi.MOD_ID, "main"),
                () -> PROTOCOL, Predicates.alwaysTrue(), Predicates.alwaysTrue()
        );
        INSTANCE.registerMessage(nextId(), BossBarUpdatePacket.class, BossBarUpdatePacket::encode,
                BossBarUpdatePacket::decode, BossBarUpdatePacket::handle
        );
    }

    private static int nextId() {
        return ID++;
    }

    public static <MSG> void sendToClient(ServerPlayer player, MSG msg) {
        INSTANCE.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
