
package com.bilibili.player_ix.blue_oceans.government.effect;

import com.bilibili.player_ix.blue_oceans.government.Government;
import net.minecraft.world.entity.LivingEntity;

public class Effect {
    protected final int level;
    private int tickTime;
    Effect(int pLevel, int pTickTime) {
        this.level = pLevel;
        this.tickTime = pTickTime;
    }

    public void tick(Government pGovernment, Iterable<? extends LivingEntity> pFollowers) {
        --this.tickTime;
    }

    public boolean shouldBeRemoved() {
        return this.tickTime <= 0;
    }
}
