
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class LivingEffect {
    public LivingEffect() {
    }

    public void applyEffectTick(Level pLevel, LivingEntity pEntity, int pEffectLevel) {
    }

    public void instantaneousEffect(Level pLevel, LivingEntity pEntity, int pEffectLevel) {
    }

    public boolean isInstantaneous() {
        return false;
    }

    public static int getId(LivingEffect pEffect) {
        return 0;
    }

    public static LivingEffect byId(int pId) {
        return null;
    }
}
