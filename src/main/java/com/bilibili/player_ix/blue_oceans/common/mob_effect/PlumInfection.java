
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumBuilder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.NineAbyss9.math.MathSupport;

public class PlumInfection
extends MobEffect {
    public PlumInfection() {
        super(MobEffectCategory.BENEFICIAL, -12189690);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof RedPlumMob))
        {
            if (Math.random() < 0.1) {
                EquipmentSlot slot = EquipmentSlot.values()[MathSupport.random.nextInt(6)];
                pLivingEntity.getItemBySlot(slot).hurtAndBreak(1, pLivingEntity, pEntity ->
                        pEntity.broadcastBreakEvent(slot));
            }
            if (pAmplifier > 0) {
                pLivingEntity.hurt(pLivingEntity.damageSources().dryOut(), pAmplifier * 0.5F);
            }
        } else {
            if (pLivingEntity instanceof PlumBuilder builder) {
                PlumBuilder.heal(builder);
            } else
                pLivingEntity.heal(1.0F);
        }
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 80 == 0;
    }
}
