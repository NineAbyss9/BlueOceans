
package com.github.player_ix.ix_api.init;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ServerAgent
implements ApiAgent {
    public Player getPlayerInstance() {
        return null;
    }

    public void addBossBar(UUID uuid, Mob mob) {}

    public void removeBossBar(UUID uuid, Mob mob) {}
}
