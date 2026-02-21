
package com.github.NineAbyss9.ix_api.api;

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

    public static ApiPose create(@SuppressWarnings("unused") String name) {
        throw new IllegalStateException("Enum not extended");
    }
}
