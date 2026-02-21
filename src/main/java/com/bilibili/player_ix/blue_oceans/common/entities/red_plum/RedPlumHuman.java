
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.api.misc.SoundCollector;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class RedPlumHuman extends RedPlumMonster {
    public RedPlumHuman(EntityType<? extends RedPlumHuman> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.xpReward = 3;
    }

    protected void registerGoals() {
        this.addMeleeAttackGoal(1, 1.2, 2);
        super.registerGoals();
    }

    protected SoundEvent getAmbientSound() {
        return SoundCollector.zombieAmbient();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundCollector.zombieHurt();
    }

    protected SoundEvent getDeathSound() {
        return SoundCollector.zombieDeath();
    }

    public ApiPose getPoses() {
        if (this.isAggressive()) {
            return ApiPose.ZOMBIE_ATTACKING;
        }
        return ApiPose.NATURAL;
    }


    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return BlueOceansEntities.RED_PLUM_SLAYER.get();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 24).add(Attributes.FOLLOW_RANGE, 42)
                .add(Attributes.ARMOR, 2).add(Attributes.KNOCKBACK_RESISTANCE, 0.1)
                .add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 4);
    }
}
