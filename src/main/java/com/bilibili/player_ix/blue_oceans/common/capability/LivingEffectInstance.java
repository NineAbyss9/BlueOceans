
package com.bilibili.player_ix.blue_oceans.common.capability;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public class LivingEffectInstance
{
    public static final int INFINITE_DURATION = -1;
    private final LivingEffect effect;
    private int duration;
    private int amplifier;
    private boolean ambient;
    public LivingEffectInstance(LivingEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient)
    {
        this.effect = pEffect;
        this.duration = pDuration;
        this.amplifier = pAmplifier;
        this.ambient = pAmbient;
    }

    public LivingEffectInstance(LivingEffect pEffect, int pDuration, int pAmplifier)
    {
        this(pEffect, pDuration, pAmplifier, false);
    }

    public LivingEffectInstance(LivingEffect pEffect, int pDuration)
    {
        this(pEffect, pDuration, 0, false);
    }

    public LivingEffectInstance(LivingEffect pEffect)
    {
        this(pEffect, 600, 0, false);
    }

    public int getDuration()
    {
        return this.duration;
    }

    public void setDuration(int pDuration)
    {
        this.duration = pDuration;
    }

    public int getAmplifier()
    {
        return this.amplifier;
    }

    public void setAmplifier(int pAmplifier)
    {
        this.amplifier = pAmplifier;
    }

    public boolean isAmbient()
    {
        return this.ambient;
    }

    public LivingEffect getEffect()
    {
        return this.effect;
    }

    /**
     * @return {@code true} if the effect should stay active after this server tick.
     */
    public boolean tickDuration()
    {
        if (this.duration == INFINITE_DURATION)
        {
            return true;
        }
        if (this.duration <= 0)
        {
            return false;
        }
        --this.duration;
        return this.duration > 0;
    }

    public void shorten(int pTicks)
    {
        if (this.duration == INFINITE_DURATION)
        {
            return;
        }
        this.duration = Math.max(0, this.duration - pTicks);
    }

    public CompoundTag save(CompoundTag pNbt)
    {
        String key = LivingEffects.getKey(this.getEffect());
        if (!key.isEmpty())
        {
            pNbt.putString("Kind", key);
        }
        pNbt.putInt("Id", LivingEffect.getId(this.getEffect()));
        this.writeDetailsTo(pNbt);
        return pNbt;
    }

    public void writeDetailsTo(CompoundTag pNbt)
    {
        pNbt.putInt("Amplifier", this.getAmplifier());
        pNbt.putInt("Duration", this.getDuration());
        pNbt.putBoolean("Ambient", this.isAmbient());
    }

    @Nullable
    public static LivingEffectInstance load(CompoundTag pNbt)
    {
        LivingEffect livingEffect = null;
        if (pNbt.contains("Kind", 8))
        {
            livingEffect = LivingEffects.byKey(pNbt.getString("Kind"));
        }
        if (livingEffect == null && pNbt.contains("Id", 3))
        {
            livingEffect = LivingEffect.byId(pNbt.getInt("Id"));
        }
        if (livingEffect == null && pNbt.contains("Id", 1))
        {
            livingEffect = LivingEffect.byId(pNbt.getByte("Id") & 0xFF);
        }
        return livingEffect == null ? null : loadSpecifiedEffect(livingEffect, pNbt);
    }

    private static LivingEffectInstance loadSpecifiedEffect(LivingEffect pEffect, CompoundTag pNbt)
    {
        int amp = pNbt.contains("Amplifier", 3) ? pNbt.getInt("Amplifier") : pNbt.getByte("Amplifier");
        int dur = pNbt.getInt("Duration");
        boolean flag = pNbt.getBoolean("Ambient");
        return new LivingEffectInstance(pEffect, dur, Math.max(0, amp), flag);
    }
}
