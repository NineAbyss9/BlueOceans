
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.github.player_ix.ix_api.util.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import static org.NineAbyss9.math.AbyssMath.random;

public class RedPlumFish
extends WaterPlumMob {
    public RedPlumFish(EntityType<? extends RedPlumFish> type, Level level) {
        super(type, level);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterPlumMobAttackGoal(this, 1.0, false));
        super.registerGoals();
    }

    protected void clientAiStep() {
        if (this.random.nextFloat() < 0.1F)
            ParticleUtil.addParticle(this.level(), ParticleTypes.BUBBLE, this.getViewVector(1.0F),
                    random(0.12), random(0.2), random(0.12));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SALMON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }
}
