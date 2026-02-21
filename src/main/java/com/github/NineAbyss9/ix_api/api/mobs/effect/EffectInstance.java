
package com.github.NineAbyss9.ix_api.api.mobs.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class EffectInstance
extends MobEffectInstance {
    public EffectInstance(MobEffect pEffect) {
        super(pEffect);
    }

    public EffectInstance(MobEffect pEffect, int pDuration) {
        super(pEffect, pDuration);
    }

    public EffectInstance(MobEffect pEffect, int pDuration, int pAmplifier) {
        super(pEffect, pDuration, pAmplifier);
    }

    public EffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
        super(pEffect, pDuration, pAmplifier, pAmbient, pVisible);
    }

    public EffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible,
                          boolean pShowIcon) {
        super(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon);
    }

    public EffectInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible,
                          boolean pShowIcon, @Nullable MobEffectInstance pHiddenEffect,
                          MobEffectInstance.FactorData pFactorData) {
        super(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon, pHiddenEffect, Optional.of(pFactorData));
    }

    public EffectInstance(MobEffectInstance pOther) {
        super(pOther);
    }

    public static MobEffectInstance create(MobEffect effect, int during, int level) {
        return new MobEffectInstance(effect, during, level);
    }

    public static MobEffectInstance create(Supplier<MobEffect> supplier, int during, int level) {
        return create(supplier.get(), during, level);
    }

    public static MobEffectInstance create(Supplier<MobEffect> supplier, int during) {
        return create(supplier.get(), during, 0);
    }
}
