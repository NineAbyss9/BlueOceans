
package com.bilibili.player_ix.blue_oceans.common.item.ts;

import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

interface IIceUtil {
    default void addEffects(LivingEntity pTarget) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                Maths.toTick(10), 0));
    }
}
