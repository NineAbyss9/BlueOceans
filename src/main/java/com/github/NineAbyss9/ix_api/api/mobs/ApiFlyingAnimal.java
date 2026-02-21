
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.FlyingAnimal;

public interface ApiFlyingAnimal
extends FlyingAnimal {
    default boolean isFlying() {
        return !((Entity)this).onGround();
    }
}
