
package com.bilibili.player_ix.blue_oceans.api.misc;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class SoundCollector {
    private SoundCollector() {
    }

    public static SoundEvent zombieAmbient() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    public static SoundEvent zombieHurt() {
        return SoundEvents.ZOMBIE_HURT;
    }

    public static SoundEvent zombieDeath() {
        return SoundEvents.ZOMBIE_DEATH;
    }
}
