
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraft.nbt.CompoundTag;

public class LivingEffectInstance {
    private final LivingEffect effect;
    private int duration;
    private int amplifier;
    private boolean ambient;
    public LivingEffectInstance(LivingEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient
                                //, @Nullable LivingEffectInstance pHiddenEffect
    ) {
        this.effect = pEffect;
        this.duration = pDuration;
        this.amplifier = pAmplifier;
        this.ambient = pAmbient;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    /**
     * Gets whether this effect originated from a beacon
     */
    public boolean isAmbient() {
        return this.ambient;
    }

    public LivingEffect getEffect() {
        return null;
    }

    public CompoundTag save(CompoundTag pNbt) {
        pNbt.putInt("Id", LivingEffect.getId(this.getEffect()));
        this.writeDetailsTo(pNbt);
        return pNbt;
    }

    public void writeDetailsTo(CompoundTag pNbt) {
        pNbt.putInt("Amplifier", this.getAmplifier());
        pNbt.putInt("Duration", this.getDuration());
        pNbt.putBoolean("Ambient", this.isAmbient());
    }
}
