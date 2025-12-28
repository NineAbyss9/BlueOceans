
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Converting
extends MobEffect {
    public Converting(int pColor) {
        super(MobEffectCategory.BENEFICIAL, pColor);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
