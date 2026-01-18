
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoPlum;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumFactory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class PlumInvade extends MobEffect {
    public PlumInvade() {
        super(MobEffectCategory.BENEFICIAL, -12189696);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof RedPlumMob)) {
            if (pLivingEntity.isAlive())
                pLivingEntity.hurt(pLivingEntity.damageSources().magic(), 1 + pAmplifier);
            else if (!pLivingEntity.level().isClientSide) {
                AbstractRedPlumMob mob = pLivingEntity.level().getNearestEntity(AbstractRedPlumMob.class,
                        TargetingConditions.forNonCombat(), null, pLivingEntity.getX(),
                        pLivingEntity.getY(), pLivingEntity.getZ(),
                        pLivingEntity.getBoundingBox().inflate(6));
                if (mob != null) {
                    if (mob instanceof PlumFactory factory) {
                        factory.setLevelPlus();
                    }
                    mob.checkAndPlusInfectLevel(pLivingEntity);
                }
                pLivingEntity.removeEffect(this);
                var plum = NeoPlum.createRandom(pLivingEntity.position(), pLivingEntity.level());
                if (plum != null) {
                    NeoPlum.addParticleAroundPlum(plum);
                }
            }
        }
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
