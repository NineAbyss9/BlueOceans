
package com.bilibili.player_ix.blue_oceans.government.effect;

import com.bilibili.player_ix.blue_oceans.government.Government;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PowerEffect
extends Effect {
    PowerEffect(int pLevel, int pTickTime) {
        super(pLevel, pTickTime);
    }

    public void tick(Government pGovernment, Iterable<? extends LivingEntity> pFollowers) {
        super.tick(pGovernment, pFollowers);
        pFollowers.forEach(livingEntity -> {
            if (livingEntity.closerThan(pGovernment.getLeader(), 16)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST));
            }
        });
    }

    public static PowerEffect get(int pLevel, int pTickTime) {
        return new PowerEffect(pLevel, pTickTime);
    }
}
