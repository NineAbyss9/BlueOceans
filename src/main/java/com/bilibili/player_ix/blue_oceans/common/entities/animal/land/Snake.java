
package com.bilibili.player_ix.blue_oceans.common.entities.animal.land;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Snake
extends BoAnimal {
    public Snake(EntityType<? extends Snake> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        super.registerGoals();
    }
}
