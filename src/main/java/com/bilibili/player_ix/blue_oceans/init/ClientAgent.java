
package com.bilibili.player_ix.blue_oceans.init;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientAgent
implements BoAgent {
    public ClientAgent() {
    }

    public Player getPlayerInstance() {
        return Minecraft.getInstance().player;
    }
}
