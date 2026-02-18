
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.world.entity.LivingEntity;

public interface ISleepMob {
    boolean canSleep();

    void setSleeping(boolean var0);

    private LivingEntity entitySelfSelector() {
        return (LivingEntity)this;
    }

    default void stopMoving() {
        entitySelfSelector().setJumping(false);
        entitySelfSelector().xxa = 0;
        entitySelfSelector().zza = 0;
    }
}
