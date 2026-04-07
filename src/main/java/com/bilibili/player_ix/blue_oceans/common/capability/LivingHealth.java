
package com.bilibili.player_ix.blue_oceans.common.capability;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@AutoRegisterCapability
public class LivingHealth implements INBTSerializable<CompoundTag>
{
    public static final ResourceLocation RESOURCE = BlueOceans.location("living_health");
    public final LivingEntity owner;
    public final BodyManager bodyManager;
    public final FeelingManager feelingManager;
    public final DrinkManager drinkManager;
    public final Map<LivingEffect, LivingEffectInstance> activeEffects = new LinkedHashMap<>();

    public LivingHealth(LivingEntity pOwner)
    {
        this.owner = pOwner;
        this.bodyManager = new BodyManager(this);
        this.feelingManager = new FeelingManager(this);
        this.drinkManager = new DrinkManager(this);
    }

    public void tick()
    {
        if (this.owner.level().isClientSide)
        {
            return;
        }
        this.bodyManager.tick();
        this.feelingManager.tick(this);
        this.drinkManager.tick();

        if (this.activeEffects.isEmpty())
        {
            return;
        }

        Iterator<Map.Entry<LivingEffect, LivingEffectInstance>> iterator = this.activeEffects.entrySet().iterator();
        List<LivingEffect> toRemove = null;
        while (iterator.hasNext())
        {
            Map.Entry<LivingEffect, LivingEffectInstance> entry = iterator.next();
            LivingEffect effect = entry.getKey();
            LivingEffectInstance instance = entry.getValue();
            if (effect.isInstantaneous())
            {
                effect.instantaneousEffect(this.owner.level(), this.owner, instance.getAmplifier());
                effect.onRemove(this.owner.level(), this.owner, instance);
                iterator.remove();
            }
            else
            {
                effect.applyEffectTick(this.owner.level(), this.owner, instance);
                if (!instance.tickDuration())
                {
                    effect.onRemove(this.owner.level(), this.owner, instance);
                    iterator.remove();
                }
            }
        }

        this.feelingManager.applyDiseaseMood(this);
    }

    public boolean hasEffect(LivingEffect pEffect)
    {
        return pEffect != null && this.activeEffects.containsKey(pEffect);
    }

    @Nullable
    public LivingEffectInstance getEffect(LivingEffect pEffect)
    {
        return this.activeEffects.get(pEffect);
    }

    public void addEffect(LivingEffectInstance pInstance)
    {
        if (pInstance == null || this.owner.level().isClientSide)
        {
            return;
        }
        this.activeEffects.put(pInstance.getEffect(), pInstance);
    }

    public void removeEffect(LivingEffect pEffect)
    {
        if (pEffect == null)
        {
            return;
        }
        LivingEffectInstance inst = this.activeEffects.remove(pEffect);
        if (inst != null)
        {
            pEffect.onRemove(this.owner.level(), this.owner, inst);
        }
    }

    public void reduceDurationOf(LivingEffect pEffect, int pTicks)
    {
        if (pEffect == null || pTicks <= 0)
        {
            return;
        }
        LivingEffectInstance inst = this.activeEffects.get(pEffect);
        if (inst == null)
        {
            return;
        }
        inst.shorten(pTicks);
        if (inst.getDuration() <= 0 && inst.getDuration() != LivingEffectInstance.INFINITE_DURATION)
        {
            pEffect.onRemove(this.owner.level(), this.owner, inst);
            this.activeEffects.remove(pEffect);
        }
    }

    public CompoundTag writeNBT()
    {
        CompoundTag tag = new CompoundTag();
        if (!this.activeEffects.isEmpty())
        {
            ListTag listtag = new ListTag();
            for (LivingEffectInstance livingEffect : this.activeEffects.values())
            {
                listtag.add(livingEffect.save(new CompoundTag()));
            }
            tag.put("ActiveEffects", listtag);
        }
        return tag;
    }

    public void readNBT(CompoundTag pTag)
    {
        this.activeEffects.clear();
        if (pTag.contains("ActiveEffects", 9))
        {
            ListTag listtag = pTag.getList("ActiveEffects", 10);
            for (int i = 0; i < listtag.size(); ++i)
            {
                CompoundTag compoundtag = listtag.getCompound(i);
                LivingEffectInstance instance = LivingEffectInstance.load(compoundtag);
                if (instance != null)
                {
                    this.activeEffects.put(instance.getEffect(), instance);
                }
            }
        }
    }

    public CompoundTag serializeNBT()
    {
        return this.writeNBT();
    }

    public void deserializeNBT(CompoundTag nbt)
    {
        this.readNBT(nbt);
    }
}
