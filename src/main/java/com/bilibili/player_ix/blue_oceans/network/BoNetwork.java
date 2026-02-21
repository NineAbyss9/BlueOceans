
package com.bilibili.player_ix.blue_oceans.network;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.network.packet.SpeedUp;
import com.github.NineAbyss9.ix_api.util.ResourceLocations;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class BoNetwork {
    private static final String PROTOCOL = "blue_oceans_packet";
    public static SimpleChannel INSTANCE;
    private static int id = 0;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(ResourceLocations.fromNamespaceAndPath(
                BlueOceans.MOD_ID, "main"), ()-> PROTOCOL, s -> true, s -> true);
        INSTANCE.registerMessage(nextId(), SpeedUp.class, SpeedUp::encode, SpeedUp::decode, SpeedUp::handle);
    }

    private static int nextId() {
        return id++;
    }

    public static <MSG> void sendToClient(ServerPlayer player, MSG message) {
        INSTANCE.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
