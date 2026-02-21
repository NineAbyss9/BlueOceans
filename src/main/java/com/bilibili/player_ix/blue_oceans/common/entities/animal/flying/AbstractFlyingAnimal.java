
package com.bilibili.player_ix.blue_oceans.common.entities.animal.flying;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import com.github.NineAbyss9.ix_api.api.mobs.ApiFlyingAnimal;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

public abstract class AbstractFlyingAnimal
extends BoAnimal
implements ApiFlyingAnimal {
    protected AbstractFlyingAnimal(EntityType<? extends AbstractFlyingAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public boolean isFlying() {
        return !this.onGround();
    }

    protected void addBehaviorGoals() {
        this.goalSelector.addGoal(4, new PanicGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource.is(DamageTypeTags.IS_FALL))
            return true;
        return super.isInvulnerableTo(pSource);
    }
}
