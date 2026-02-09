
package com.bilibili.player_ix.blue_oceans.api.mob;

public interface RedPlumMob {
    default boolean isControlledByGirl() {
        return false;
    }

    default void setControlledByGirl(boolean flag) {
    }

    default int getLevel() {
        return 1;
    }

    default boolean shouldAttackOtherMobs() {
        return true;
    }

    default boolean isException() {
        return false;
    }

    MobTypes getMobTypes();
}
