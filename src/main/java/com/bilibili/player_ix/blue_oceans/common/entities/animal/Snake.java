
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Snake
extends BoAnimal {
    public Snake(EntityType<? extends Snake> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }
}
