
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Comfortable
extends MobEffect {
    public Comfortable() {
        super(MobEffectCategory.BENEFICIAL, 16777045);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.heal(BoCommonConfig.COMFORTABLE_HEAL_AMOUNT.get().floatValue());
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 60 == 0;
    }
}
