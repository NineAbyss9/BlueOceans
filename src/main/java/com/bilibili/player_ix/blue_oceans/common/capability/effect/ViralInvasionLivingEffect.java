
package com.bilibili.player_ix.blue_oceans.common.capability.effect;

import com.bilibili.player_ix.blue_oceans.common.capability.LivingEffect;
import com.bilibili.player_ix.blue_oceans.common.capability.LivingHealth;
import com.bilibili.player_ix.blue_oceans.common.entities.Virus;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/** Active viral infection: periodic systemic damage and weakness. Mood impact in {@link LivingHealth#tick()}. */
public class ViralInvasionLivingEffect extends LivingEffect
{
    private static final int DAMAGE_INTERVAL = 40;
    private static final int WEAKNESS_INTERVAL = 200;
    public ViralInvasionLivingEffect() {}

    public void applyEffectTick(Level pLevel, LivingEntity pEntity, int pEffectLevel)
    {
        if (pLevel.isClientSide)
        {
            return;
        }
        Virus.VirusType virusType = Virus.VirusType.byId(pEffectLevel);
        if (pEntity.tickCount % DAMAGE_INTERVAL == 0)
        {//TODO
            DamageSource src = pEntity.damageSources().magic();
            float baseDmg = 0.35F;
            float dmg = baseDmg * virusType.getDamageMultiplier() + baseDmg * (pEffectLevel + 1);
            pEntity.hurt(src, dmg);
        }
        if (pEntity.tickCount % WEAKNESS_INTERVAL == 0)
        {
            pEntity.addEffect(new MobEffectInstance(virusType.getAssociatedEffect(), 120, Math.min(pEffectLevel, 2)));
        }
    }
}
