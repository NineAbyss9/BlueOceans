
package com.bilibili.player_ix.blue_oceans.common.capability.effect;

import com.bilibili.player_ix.blue_oceans.common.capability.BoCapabilities;
import com.bilibili.player_ix.blue_oceans.common.capability.LivingEffect;
import com.bilibili.player_ix.blue_oceans.common.capability.LivingEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * Represents an active immune response: slowly restores health and shortens viral invasion when both
 * are present (game abstraction of clearance).
 */
public class ImmuneResponseLivingEffect extends LivingEffect
{
    private static final int HEAL_INTERVAL = 60;
    private static final int VIRAL_SHRED_INTERVAL = 80;

    public ImmuneResponseLivingEffect()
    {
    }

    public void applyEffectTick(Level pLevel, LivingEntity pEntity, int pEffectLevel)
    {
        if (pLevel.isClientSide)
        {
            return;
        }
        int amp = Math.max(0, pEffectLevel);
        if (pEntity.tickCount % HEAL_INTERVAL == 0)
        {
            float heal = 0.25F + 0.15F * amp;
            pEntity.heal(heal);
        }
        if (pEntity.tickCount % VIRAL_SHRED_INTERVAL == 0)
        {
            pEntity.getCapability(BoCapabilities.LIVING_HEALTH).ifPresent(h ->
                    h.reduceDurationOf(LivingEffects.VIRAL_INVASION, 20 + 15 * amp));
        }
    }
}
