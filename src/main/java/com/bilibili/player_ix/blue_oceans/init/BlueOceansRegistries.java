
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.api.potion.PlumDishPotion;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class BlueOceansRegistries {
    static {
        register();
    }

    public static final IForgeRegistry<PlumDishPotion> PLUM_DISH_POTIONS
            = RegistryManager.ACTIVE.getRegistry(Keys.PLUM_DISH_POTION);

    public static void register() {
        Keys.register();
    }

    public static class Keys {
        public static final ResourceKey<Registry<PlumDishPotion>> PLUM_DISH_POTION
                = key("blue_oceans:plum_dish_potion");

        private static void register() {
        }

        private static <T> ResourceKey<Registry<T>> key(String name) {
            return ResourceKey.createRegistryKey(new ResourceLocation(name));
        }
    }
}
