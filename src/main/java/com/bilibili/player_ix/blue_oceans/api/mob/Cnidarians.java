
package com.bilibili.player_ix.blue_oceans.api.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public interface Cnidarians {
    Predicate<LivingEntity> NOT_CNIDARIANS = living -> !(living instanceof Cnidarians);
    void sting(LivingEntity pEntity);

    Vec3 getSize();

    private Entity selfSelector() {
        return (Entity)this;
    }

    default void tickSting() {
        this.tickSting(NOT_CNIDARIANS);
    }

    default void tickSting(Predicate<LivingEntity> toIgnore) {
        if (!selfSelector().level().isClientSide) {
            List<LivingEntity> entities = selfSelector().level().getEntitiesOfClass(LivingEntity.class, selfSelector().getBoundingBox()
                    , toIgnore);
            if (!entities.isEmpty())
                entities.forEach(this::sting);
        }
    }
}
