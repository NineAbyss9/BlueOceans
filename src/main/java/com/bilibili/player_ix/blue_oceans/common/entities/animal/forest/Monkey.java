
package com.bilibili.player_ix.blue_oceans.common.entities.animal.forest;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import com.github.player_ix.ix_api.api.mobs.MobUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Monkey
extends BoAnimal {
    public Monkey(EntityType<? extends Monkey> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    private void jumpToLookAt() {
        MobUtils.moveToLookAt(this, 0.3);
    }
}
