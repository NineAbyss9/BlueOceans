
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FriendlyPlumInvade
extends MobEffect {
    public FriendlyPlumInvade() {
        super(MobEffectCategory.BENEFICIAL, -12189696);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof RedPlumMob)) {
            pLivingEntity.hurt(pLivingEntity.damageSources().dryOut(), 1 + pAmplifier);
        }
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 10 == 0;
    }
}
