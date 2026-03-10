
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumBuilder;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.NineAbyss9.math.MathSupport;

import java.util.List;

public class PlumInfection
extends MobEffect {
    public PlumInfection() {
        super(MobEffectCategory.BENEFICIAL, -12189690);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof RedPlumMob)
        {
            boolean f = pAmplifier > 2;
            if (pLivingEntity.tickCount % (f ? 10 : 40) == 0) {
                if (pLivingEntity instanceof PlumBuilder builder) {
                    PlumBuilder.heal(builder);
                } else
                    pLivingEntity.heal(1.0F);
            }
        } else {
            if (Math.random() < 0.05) {
                EquipmentSlot slot = EquipmentSlot.values()[MathSupport.random.nextInt(6)];
                pLivingEntity.getItemBySlot(slot).hurtAndBreak(1, pLivingEntity, pEntity ->
                        pEntity.broadcastBreakEvent(slot));
            }
            if (pAmplifier > 0) {
                pLivingEntity.hurt(pLivingEntity.damageSources().dryOut(), pAmplifier * 0.5F);
            }
            Level level = pLivingEntity.level();
            if (!pLivingEntity.isAlive() && pAmplifier > 1) {
                if (!level.isClientSide && pLivingEntity.deathTime == 10) {
                    List<AbstractRedPlumMob> mobs = level.getEntitiesOfClass(AbstractRedPlumMob.class,
                            pLivingEntity.getBoundingBox().inflate(8));
                    if (!mobs.isEmpty()) {
                        for (AbstractRedPlumMob mob : mobs) {
                            mob.setKillsPlus();
                            mob.checkAndPlusInfectLevel(pLivingEntity);
                        }
                    }
                    if (pLivingEntity.removeEffect(this)) {
                        if (RedPlumUtil.likeHuman(pLivingEntity))
                            RedPlumUtil.spawnRedPlumHuman(level, pLivingEntity);
                        else if (RedPlumUtil.likeVillager(pLivingEntity))
                            RedPlumUtil.spawnRedPlumVillager(level, pLivingEntity);
                        else {
                            RedPlumUtil.spawnBase(level, pLivingEntity.position());
                        }
                    }
                }
            }
        }
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 10 == 0;
    }
}
