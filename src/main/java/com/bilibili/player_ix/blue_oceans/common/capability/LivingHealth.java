
package com.bilibili.player_ix.blue_oceans.common.capability;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

//@AutoRegisterCapability
public class LivingHealth implements INBTSerializable<CompoundTag> {
    public static final ResourceLocation RESOURCE = BlueOceans.location("living_health");
    public final LivingEntity owner;
    public BodyManager bodyManager;
    public Map<LivingEffect, LivingEffectInstance> activeEffects = Maps.newHashMap();
    public LivingHealth(LivingEntity pOwner) {
        this.owner = pOwner;
        this.bodyManager = new BodyManager(this);
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

    }

    public CompoundTag serializeNBT() {
        return this.writeNBT();
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.readNBT(nbt);
    }
}
