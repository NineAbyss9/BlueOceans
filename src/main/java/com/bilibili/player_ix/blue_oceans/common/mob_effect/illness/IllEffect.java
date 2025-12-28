
package com.bilibili.player_ix.blue_oceans.common.mob_effect.illness;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class IllEffect
extends MobEffect {
    private RemoveReason removeReason = null;
    public IllEffect(int pColor) {
        super(MobEffectCategory.HARMFUL, pColor);
    }

    public void onRemove(LivingEntity pLiving, int pLevel, RemoveReason reason) {
        this.removeReason = reason;
    }

    public void onRemove(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier, RemoveReason pReason) {
        this.removeReason = pReason;
    }

    public RemoveReason getRemoveReason() {
        return removeReason;
    }

    public boolean isRemoved() {
        return removeReason != null;
    }

    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        if (!isRemoved())
            this.onRemove(pLivingEntity, pAttributeMap, pAmplifier, RemoveReason.NATURAL);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
    }
}
