
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/** Non-specific malaise: occasional minor harm when untreated. */
public class IllLivingEffect extends LivingEffect
{
    private static final int TICK_RATE = 100;

    public IllLivingEffect()
    {
    }

    public void applyEffectTick(Level pLevel, LivingEntity pEntity, int pEffectLevel)
    {
        if (pLevel.isClientSide || pEntity.tickCount % TICK_RATE != 0)
        {
            return;
        }
        float amount = 0.5F + 0.25F * pEffectLevel;
        DamageSource src = pEntity.damageSources().generic();
        pEntity.hurt(src, amount);
    }
}
