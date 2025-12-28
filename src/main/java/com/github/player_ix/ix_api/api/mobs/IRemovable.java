
package com.github.player_ix.ix_api.api.mobs;

import org.nine_abyss.annotation.Unused;

@Unused
public interface IRemovable {
    default boolean canRemove() {
        return true;
    }

    default void canRemove(boolean flag) {
    }
}
