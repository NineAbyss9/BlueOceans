
package com.bilibili.player_ix.blue_oceans.compat.spore;

import com.bilibili.player_ix.blue_oceans.compat.ICompatable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class SporeCompat
implements ICompatable {
    public static final String SPORE = "spore";
    public void setup(FMLCommonSetupEvent event) {
    }

    public static boolean isSporeLoaded() {
        return ModList.get().isLoaded(SPORE);
    }

    public static MobEffect getSporeEffect(String name) {
        return ICompatable.getEffect(SPORE, name);
    }

    public static SoundEvent getSporeSound(String name) {
        return ICompatable.getSound(SPORE, name);
    }

    public static EntityType<?> getSporeEntity(String name) {
        return ICompatable.getEntityType(SPORE, name);
    }
}
