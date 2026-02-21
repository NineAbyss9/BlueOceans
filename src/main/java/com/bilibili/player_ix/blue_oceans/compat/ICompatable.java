
package com.bilibili.player_ix.blue_oceans.compat;

import com.github.NineAbyss9.ix_api.util.ResourceLocations;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

public interface ICompatable {
    void setup(FMLCommonSetupEvent event);

    static SoundEvent getSound(String path, String name) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocations.fromNamespaceAndPath(path, name));
    }

    static MobEffect getEffect(String path, String name) {
        return ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocations.fromNamespaceAndPath(path, name));
    }

    static EntityType<?> getEntityType(String path, String name) {
        return ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocations.fromNamespaceAndPath(path, name));
    }
}
