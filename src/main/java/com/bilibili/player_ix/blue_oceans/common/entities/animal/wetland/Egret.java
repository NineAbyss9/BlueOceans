
package com.bilibili.player_ix.blue_oceans.common.entities.animal.wetland;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

//鹭
public class Egret
extends BoAnimal
implements IAnimatedMob {
    public Egret(EntityType<? extends Egret> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    public List<AnimationState> getAllAnimations() {
        return List.of();
    }
}
