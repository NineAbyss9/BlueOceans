
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;

public interface ApiTargeting extends Targeting {
    void setTarget(LivingEntity living);
}
