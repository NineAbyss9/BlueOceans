
package com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.WaterAnimal;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class Shrimp
extends WaterAnimal
implements IAnimatedMob {
    public AnimationState idle = new AnimationState();
    public AnimationState swim = new AnimationState();
    public Shrimp(EntityType<? extends Shrimp> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(tickCount);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(swim);
    }
}
