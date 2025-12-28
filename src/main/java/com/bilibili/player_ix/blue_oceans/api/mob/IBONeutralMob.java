
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.world.entity.NeutralMob;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface IBONeutralMob extends NeutralMob {
    default int getRemainingPersistentAngerTime() {
        return 0;
    }

    default void setRemainingPersistentAngerTime(int i) {
    }

    @Nullable
    default UUID getPersistentAngerTarget() {
        return null;
    }

    default void setPersistentAngerTarget(@Nullable UUID uuid) {
    }

    default void startPersistentAngerTimer() {
    }
}
