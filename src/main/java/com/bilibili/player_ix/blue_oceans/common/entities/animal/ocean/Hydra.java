
package com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean;

import com.bilibili.player_ix.blue_oceans.api.mob.Cnidarians;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.WaterAnimal;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Hydra
extends WaterAnimal
implements Cnidarians {
    public AnimationState idle = new AnimationState();
    public Hydra(EntityType<? extends Hydra> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        super.tick();
        this.tickSting();
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(tickCount);
    }

    public Vec3 getSize() {
        return new Vec3(0.35d, 1d, 0.35d);
    }

    public void sting(LivingEntity pEntity) {
        pEntity.hurt(this.damageSources().cactus(), 0.25F);
    }
}
