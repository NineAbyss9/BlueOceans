
package com.bilibili.player_ix.blue_oceans.common.capability;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.LinkedHashMap;
import java.util.Map;

//@AutoRegisterCapability
public class LivingHealth implements INBTSerializable<CompoundTag> {
    public static final ResourceLocation RESOURCE = BlueOceans.location("living_health");
    public final LivingEntity owner;
    public BodyManager bodyManager;
    public FeelingManager feelingManager;
    public Map<LivingEffect, LivingEffectInstance> activeEffects = new LinkedHashMap<>();
    public LivingHealth(LivingEntity pOwner) {
        this.owner = pOwner;
        this.bodyManager = new BodyManager(this);
        this.feelingManager = new FeelingManager(this);
    }

    public void tick() {
        if (!activeEffects.isEmpty()) {
            this.activeEffects.forEach((livingEffect, livingEffectInstance) -> {
                if (livingEffect.isInstantaneous()) {
                    livingEffect.instantaneousEffect(owner.level(), owner, livingEffectInstance.getAmplifier());
                    livingEffect.onRemove(owner.level(), owner, livingEffectInstance.getAmplifier());
                    this.activeEffects.remove(livingEffect, livingEffectInstance);
                }
                else
                    livingEffect.applyEffectTick(owner.level(), owner, livingEffectInstance.getAmplifier());
            });
        }
    }

    public CompoundTag writeNBT() {
        CompoundTag tag = new CompoundTag();
        if (!this.activeEffects.isEmpty()) {
            ListTag listtag = new ListTag();
            for (LivingEffectInstance livingEffect : this.activeEffects.values()) {
                listtag.add(livingEffect.save(new CompoundTag()));
            }
            tag.put("ActiveEffects", listtag);
        }
        return tag;
    }

    public void readNBT(CompoundTag pTag) {
        if (pTag.contains("ActiveEffects", 9)) {
            ListTag listtag = pTag.getList("ActiveEffects", 10);
            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                LivingEffectInstance instance = LivingEffectInstance.load(compoundtag);
                if (instance != null) {
                    this.activeEffects.put(instance.getEffect(), instance);
                }
            }
        }
    }

    public CompoundTag serializeNBT() {
        return this.writeNBT();
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.readNBT(nbt);
    }
}
