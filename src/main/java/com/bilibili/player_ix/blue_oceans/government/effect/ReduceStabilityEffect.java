
package com.bilibili.player_ix.blue_oceans.government.effect;

import com.bilibili.player_ix.blue_oceans.government.Government;
import net.minecraft.world.entity.LivingEntity;

public class ReduceStabilityEffect
extends Effect {
    ReduceStabilityEffect(int pLevel, int pTickTime) {
        super(pLevel, pTickTime);
    }

    public void tick(Government pGovernment, Iterable<? extends LivingEntity> pFollowers) {
        super.tick(pGovernment, pFollowers);
        this.reduceStability(pGovernment);
    }

    private void reduceStability(Government pGovernment) {
        pGovernment.setStability(pGovernment.getStability() - this.level * 0.0005F);
    }

    public static ReduceStabilityEffect get(int pReduceValue, int pTickTime) {
        return new ReduceStabilityEffect(pReduceValue, pTickTime);
    }
}
