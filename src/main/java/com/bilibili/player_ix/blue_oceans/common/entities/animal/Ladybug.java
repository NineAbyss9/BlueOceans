
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class Ladybug
extends BoAnimal
implements IAnimatedMob {
    public AnimationState fly = new AnimationState();
    public Ladybug(EntityType<? extends Ladybug> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(fly);
    }
}
