
package com.bilibili.player_ix.blue_oceans.common.mob_effect.illness;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class Fever
extends IllEffect {
    public Fever(int pColor) {
        super(pColor);
    }

    public void onRemove(LivingEntity pLiving, int pLevel, RemoveReason reason) {
        if (pLevel > 3) {
            pLiving.addEffect(new MobEffectInstance(MobEffects.CONFUSION));
        }
        super.onRemove(pLiving, pLevel, reason);
    }
}
