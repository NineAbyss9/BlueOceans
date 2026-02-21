
package com.bilibili.player_ix.blue_oceans.common.capability;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

//@AutoRegisterCapability
public class LivingHealth implements INBTSerializable<CompoundTag> {
    public Map<LivingEffect, LivingEffectInstance> activeEffects = Maps.newHashMap();
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
