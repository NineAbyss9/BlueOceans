
package com.bilibili.player_ix.blue_oceans.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**Util for client & server*/
public class SideHandler {
    private SideHandler() {
    }

    public static boolean isClientSide() {
        return FMLEnvironment.dist.isClient();
    }

    public static boolean isServerSide() {
        return FMLEnvironment.dist.isDedicatedServer();
    }

    public static boolean checkLevel() {
        return Minecraft.getInstance().level != null;
    }

    @SuppressWarnings("all")
    public static boolean checkConnection() {
        return Minecraft.getInstance() != null && Minecraft.getInstance().getConnection() != null;
    }
}
