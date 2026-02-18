
package com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumMonster;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.github.player_ix.ix_api.api.ApiPose;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class RedPlumIllager  extends RedPlumMonster {
    protected RedPlumIllager(EntityType<? extends AbstractRedPlumMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    protected SoundEvent getAmbientSound() {
        return BlueOceansSounds.RED_PLUM_CULTIST_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return BlueOceansSounds.RED_PLUM_CULTIST_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return BlueOceansSounds.RED_PLUM_CULTIST_DIES.get();
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return pDistanceToClosestPlayer > 10000;
    }

    public ApiPose getPoses() {
        if (this.isAggressive()) {
            return ApiPose.ATTACKING;
        }
        return ApiPose.CROSSED;
    }
}
