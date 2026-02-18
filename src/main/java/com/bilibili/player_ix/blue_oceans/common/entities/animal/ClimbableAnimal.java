
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.github.player_ix.ix_api.api.mobs.IFlagMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;

public class ClimbableAnimal
extends BoAnimal
implements IFlagMob {
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    public ClimbableAnimal(EntityType<? extends BoAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WallClimberNavigation(this, pLevel);
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return this.isFlag(1);
    }

    public void setClimbing(boolean flag) {
        this.setFlag(flag ? 1 : 0);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(ClimbableAnimal.class, EntityDataSerializers.INT);
    }
}
