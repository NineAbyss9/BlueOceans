
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class LivingEffect
{
    private int registryId = -1;
    private String registryKey = "";

    public LivingEffect()
    {
    }

    void bindRegistryId(int pId, String pKey)
    {
        this.registryId = pId;
        this.registryKey = pKey;
    }

    public int getRegistryId()
    {
        return this.registryId;
    }

    public String getRegistryKey()
    {
        return this.registryKey;
    }

    public void applyEffectTick(Level pLevel, LivingEntity pEntity, LivingEffectInstance pInstance)
    {
        this.applyEffectTick(pLevel, pEntity, pInstance.getAmplifier());
    }

    public void applyEffectTick(Level pLevel, LivingEntity pEntity, int pEffectLevel)
    {
    }

    public void instantaneousEffect(Level pLevel, LivingEntity pEntity, int pEffectLevel)
    {
    }

    public void onRemove(Level pLevel, LivingEntity pEntity, LivingEffectInstance pInstance)
    {
        this.onRemove(pLevel, pEntity, pInstance.getAmplifier());
    }

    public void onRemove(Level pLevel, LivingEntity pEntity, int pEffectLevel)
    {
    }

    public boolean isInstantaneous()
    {
        return false;
    }

    public static int getId(LivingEffect pEffect)
    {
        return pEffect == null ? -1 : pEffect.getRegistryId();
    }

    public static LivingEffect byId(int pId)
    {
        return LivingEffects.byId(pId);
    }
}
