
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.phys.Vec3;

public interface TypedMob<T> extends VariantHolder<T> {
    T getKind();

    Vec3 getSize();

    default T getVariant() {
        return this.getKind();
    }

    interface Sized {
        Vec3 size();
    }
}
