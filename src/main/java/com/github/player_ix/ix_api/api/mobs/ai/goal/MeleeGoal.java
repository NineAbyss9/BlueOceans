
package com.github.player_ix.ix_api.api.mobs.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

public class MeleeGoal
extends ApiMeleeAttackGoal {
    public MeleeGoal(PathfinderMob finder, double speed) {
        super(finder, speed);
    }

    protected void checkAndPerformAttack(LivingEntity pTarget, double pRange) {
    }
}
