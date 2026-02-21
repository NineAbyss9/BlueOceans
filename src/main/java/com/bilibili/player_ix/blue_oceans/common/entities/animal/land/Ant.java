
package com.bilibili.player_ix.blue_oceans.common.entities.animal.land;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Ant
extends BoAnimal {
    public Ant(EntityType<? extends Ant> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void aiStep() {
        super.aiStep();
    }

    public void buildNest() {

    }
}
