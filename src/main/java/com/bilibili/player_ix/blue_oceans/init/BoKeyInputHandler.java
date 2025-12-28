
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.keys.RingKey;
import com.bilibili.player_ix.blue_oceans.keys.SpeedUpKey;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BoKeyInputHandler {
    public static final RingKey RING = new RingKey();
    public static final SpeedUpKey SPEED_UP = new SpeedUpKey();
    private BoKeyInputHandler() {
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(RING);
        event.register(SPEED_UP);
    }

    @Mod.EventBusSubscriber(Dist.CLIENT)
    public static class KeyInputListener {
        @SubscribeEvent
        public static void handle(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null) {
                RING.consumeClick();
                SPEED_UP.consumeClick();
            }
        }
    }
}
