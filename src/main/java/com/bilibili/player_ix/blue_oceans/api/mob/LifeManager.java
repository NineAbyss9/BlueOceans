
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.world.entity.Mob;

public class LifeManager {
    private final Mob mob;
    private final AgeManager ageManager;
    public LifeManager(Mob pMob) {
        this.mob = pMob;
        ageManager = new AgeManager();
    }

    public void tick() {
        ageManager().plus();
    }

    public AgeManager ageManager() {
        return ageManager;
    }
}
