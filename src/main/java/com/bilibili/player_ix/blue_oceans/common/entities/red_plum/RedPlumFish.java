
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

public class RedPlumFish
extends WaterPlumMob {
    public RedPlumFish(EntityType<? extends RedPlumFish> type, Level level) {
        super(type, level);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterPlumMobAttackGoal(this, 1.0, false));
        super.registerGoals();
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.65F;
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

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.FOLLOW_RANGE, 45).add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ARMOR, 2).add(Attributes.MOVEMENT_SPEED, 1)
                .add(ForgeMod.SWIM_SPEED.get(), 10);
    }
}
