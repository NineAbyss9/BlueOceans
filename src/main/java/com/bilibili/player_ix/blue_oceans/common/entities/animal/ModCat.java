
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.ISleepMob;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.Level;

public class ModCat
extends BoAnimal
implements ISleepMob {
    public Cat cat;
    public ModCat(EntityType<? extends BoAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.CAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    public boolean canSleep() {
        return false;
    }

    public boolean isSleeping() {
        return super.isSleeping();
    }

    public void setSleeping(boolean var0) {

    }

    public void meow() {
        playSound(getAmbientSound());
    }
}
