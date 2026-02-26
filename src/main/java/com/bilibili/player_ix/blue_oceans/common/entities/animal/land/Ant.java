
package com.bilibili.player_ix.blue_oceans.common.entities.animal.land;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import com.github.NineAbyss9.ix_api.api.mobs.OwnableMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Ant
extends BoAnimal {
    protected static final EntityDataAccessor<Integer> DATA_TYPE;
    public Ant(EntityType<? extends Ant> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_TYPE, 0);
    }

    public void aiStep() {
        super.aiStep();
    }

    protected void registerGoals() {
        OwnableMob.addBehaviorGoals(this, 5, 0.8, 10.0F, true, true);
    }

    public void buildNest() {

    }

    static {
        DATA_TYPE = SynchedEntityData.defineId(Ant.class, EntityDataSerializers.INT);
    }
}
