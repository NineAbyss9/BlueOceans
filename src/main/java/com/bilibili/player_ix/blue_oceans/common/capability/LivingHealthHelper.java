
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraft.world.entity.LivingEntity;

/** API helpers for attaching custom health / disease state to any living entity with the capability. */
public final class LivingHealthHelper
{
    private LivingHealthHelper()
    {
        throw new AssertionError();
    }

    public static void addViralInvasion(LivingEntity pEntity, int pDurationTicks, int pAmplifier)
    {
        pEntity.getCapability(BoCapabilities.LIVING_HEALTH).ifPresent(h ->
                h.addEffect(new LivingEffectInstance(LivingEffects.VIRAL_INVASION, pDurationTicks,
                        Math.max(0, pAmplifier), false)));
    }

    public static void addImmuneResponse(LivingEntity pEntity, int pDurationTicks, int pAmplifier)
    {
        pEntity.getCapability(BoCapabilities.LIVING_HEALTH).ifPresent(h ->
                h.addEffect(new LivingEffectInstance(LivingEffects.IMMUNE_RESPONSE, pDurationTicks,
                        Math.max(0, pAmplifier), false)));
    }

    public static void addIllness(LivingEntity pEntity, int pDurationTicks, int pAmplifier)
    {
        pEntity.getCapability(BoCapabilities.LIVING_HEALTH).ifPresent(h ->
                h.addEffect(new LivingEffectInstance(LivingEffects.ILL, pDurationTicks,
                        Math.max(0, pAmplifier), false)));
    }

    public static void clearInfection(LivingEntity pEntity)
    {
        pEntity.getCapability(BoCapabilities.LIVING_HEALTH).ifPresent(h -> {
            h.removeEffect(LivingEffects.VIRAL_INVASION);
            h.removeEffect(LivingEffects.ILL);
        });
    }
}
