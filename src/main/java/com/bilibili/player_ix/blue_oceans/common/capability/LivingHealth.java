
package com.bilibili.player_ix.blue_oceans.common.capability;

//import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.NineAbyss9.util.lister.Lister;
import org.NineAbyss9.util.lister.SubLister;

//@AutoRegisterCapability
public class LivingHealth implements INBTSerializable<CompoundTag> {
    public Lister<LivingEffect> activeEffects = SubLister.of();
    public CompoundTag writeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("CACHE", 0);
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
