
package com.bilibili.player_ix.blue_oceans.common.item.seed;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface SeedItem {
    EntityType<?> getSeedEntity();

    default void spawnEntity(Level pLevel, Vec3 pVector) {
        Entity entity = this.getSeedEntity().create(pLevel);
        if (entity != null) {
            entity.moveTo(pVector);
            pLevel.addFreshEntity(entity);
        }
    }
}
