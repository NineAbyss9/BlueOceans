
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface IAnimatedMob {
    List<AnimationState> getAllAnimations();

    default AnimationState getAnimation(int pAni) {
        return this.getAllAnimations().get(pAni);
    }

    default void startAfterStop(AnimationState state) {
        this.stopAllAnimations();
        state.startIfStopped(((Entity)this).tickCount);
    }

    default void stopAllAnimations() {
        for (AnimationState state : this.getAllAnimations()) {
            state.stop();
        }
    }
}
