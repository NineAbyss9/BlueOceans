
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class StunEffect
extends MobEffect {
    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 16762624);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Mob) {
            ((Mob)pLivingEntity).setNoAi(true);
        }
        pLivingEntity.setJumping(false);
    }

    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        return -1.0D;
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        if (pLivingEntity instanceof Mob) {
            ((Mob)pLivingEntity).setNoAi(false);
        }
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
