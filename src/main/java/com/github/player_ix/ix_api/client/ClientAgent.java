
package com.github.player_ix.ix_api.client;

import com.github.player_ix.ix_api.init.Agent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ClientAgent
implements Agent {
    public Player getPlayerInstance() {
        return Minecraft.getInstance().player;
    }

    public void addBossBar(UUID uuid, Mob mob) {
    }

    public void removeBossBar(UUID uuid, Mob mob) {
    }
}
