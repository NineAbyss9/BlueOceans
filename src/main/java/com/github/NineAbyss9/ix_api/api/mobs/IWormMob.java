
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Predicate;

@org.NineAbyss9.annotation.Unused
public interface IWormMob {
    Predicate<LivingEntity> NOT_WORM = livingEntity -> !(livingEntity instanceof IWormMob);
    Predicate<Entity> IS_WORM = entity -> entity instanceof IWormMob;
}
