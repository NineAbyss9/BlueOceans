
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior;

import net.minecraft.world.entity.Mob;

import java.util.EnumSet;

public class RandomLookBehavior
extends Behavior {
    protected final Mob mob;
    public RandomLookBehavior(Mob pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(BehaviorFlag.LOOK));
    }


}
