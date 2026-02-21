
package com.bilibili.player_ix.blue_oceans.common.entities.animal.land;

import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import com.github.NineAbyss9.ix_api.api.mobs.OwnableMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Snail
extends BoAnimal {
    public AnimationState idle = new AnimationState();
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    public Snail(EntityType<? extends Snail> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        OwnableMob.addBehaviorGoals(this, 4, 0.8, 4, true, false);
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public boolean isFlag(int flag) {
        return this.getFlag() == flag;
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.INT);
    }
}
