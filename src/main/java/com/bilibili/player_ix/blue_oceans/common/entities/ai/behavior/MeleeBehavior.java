
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

public class MeleeBehavior
extends AttackBehavior {
    public MeleeBehavior(PathfinderMob pMob, double pSpeed) {
        super(pMob, pSpeed, false);
    }

    protected void checkAndPerformAttack(LivingEntity pTarget, double pDistance) {
    }
}
