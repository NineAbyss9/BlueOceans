
package com.bilibili.player_ix.blue_oceans.network.packet;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.traffic.AbstractTrafficUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpeedUp {
    public final boolean isSpeedUp;
    public SpeedUp(boolean pSpeedUp) {
        this.isSpeedUp = pSpeedUp;
    }

    public static void encode(SpeedUp speedUp, FriendlyByteBuf buffer) {
        buffer.writeBoolean(speedUp.isSpeedUp);
    }

    public static SpeedUp decode(FriendlyByteBuf buffer) {
        return new SpeedUp(buffer.readBoolean());
    }

    public static void handle(SpeedUp speedUp, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = ctx.get().getSender();
            if (ctx.get().getDirection().getReceptionSide().equals(LogicalSide.CLIENT)) {
                player = BlueOceans.agent.getPlayerInstance();
            }
            if (player != null) {
                Entity vehicle = player.getVehicle();
                if (vehicle instanceof AbstractTrafficUtil trafficUtil) {
                    trafficUtil.setSpeedy(speedUp.isSpeedUp);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
