
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class RedPlumArrow
extends Arrow {
    public RedPlumArrow(EntityType<? extends RedPlumArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public RedPlumArrow(Level pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    public RedPlumArrow(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        pLiving.addEffect(new MobEffectInstance(BlueOceansMobEffects.PLUM_INVADE.get()));
        super.doPostHurtEffects(pLiving);
    }
}
