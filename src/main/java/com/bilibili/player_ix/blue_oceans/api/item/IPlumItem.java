
package com.bilibili.player_ix.blue_oceans.api.item;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.github.player_ix.ix_api.api.mobs.effect.EffectInstance;
import net.minecraft.world.entity.LivingEntity;

public interface IPlumItem {
    default void addPlumEffect(LivingEntity pTarget) {
        pTarget.addEffect(EffectInstance.create(BlueOceansMobEffects.FRIENDLY_PLUM_INVADE, 140, 1));
    }

    default void addPlumEffect(LivingEntity pTarget, int pLevel) {
        pTarget.addEffect(EffectInstance.create(BlueOceansMobEffects.FRIENDLY_PLUM_INVADE, 140, pLevel));
    }
}
