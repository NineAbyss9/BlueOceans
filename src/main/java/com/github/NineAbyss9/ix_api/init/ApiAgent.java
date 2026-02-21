
package com.github.NineAbyss9.ix_api.init;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public interface ApiAgent {
    Player getPlayerInstance();

    void addBossBar(UUID uuid, Mob mob);

    void removeBossBar(UUID uuid, Mob mob);
}
