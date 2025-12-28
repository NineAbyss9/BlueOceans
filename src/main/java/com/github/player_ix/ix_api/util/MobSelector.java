
package com.github.player_ix.ix_api.util;

import net.minecraft.world.entity.monster.Enemy;

import java.util.function.Predicate;

public final class MobSelector {

    public static <T> Predicate<T> isEnemy() {
        return entity -> entity instanceof Enemy;
    }
}
