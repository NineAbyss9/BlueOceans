
package com.github.player_ix.ix_api.api;

import net.minecraftforge.common.IExtensibleEnum;

public enum ApiPose implements IExtensibleEnum {
    ATTACKING,
    BOW_AND_ARROW,
    CHASING,
    CROSSBOW_CHARGE,
    CROSSBOW_HOLD,
    CROSSED,
    COMMANDING,
    NATURAL,
    SPELL_AND_WEAPON,
    SPELL_CASTING,
    ZOMBIE_ATTACKING;

    ApiPose() {
    }

    @SuppressWarnings("unused")
    public static ApiPose create(String name) {
        throw new IllegalStateException("Enum not extended");
    }
}
