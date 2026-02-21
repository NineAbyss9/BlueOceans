
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;

public class Gecko
extends BoAnimal {
    public AnimationState idle = new AnimationState();
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    public Gecko(EntityType<? extends Gecko> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide)
            this.setClimbing(this.horizontalCollision);
    }

    public boolean onClimbable() {
        return (this.entityData.get(DATA_FLAGS) & 1) != 0;
    }

    public void setClimbing(boolean climbing) {
        int i = this.entityData.get(DATA_FLAGS);
        if (climbing)
            i = (i | 1);
        else
            i = (i & -2);
        this.entityData.set(DATA_FLAGS, i);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WallClimberNavigation(this, pLevel);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Gecko.class, EntityDataSerializers.INT);
    }
}
