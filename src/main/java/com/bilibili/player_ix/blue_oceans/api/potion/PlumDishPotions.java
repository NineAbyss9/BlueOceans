
package com.bilibili.player_ix.blue_oceans.api.potion;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class PlumDishPotions {
    public static final PlumDishPotion EMPTY;
    public static final PlumDishPotion WATER;
    public static final PlumDishPotion MUNDANE;
    public static final PlumDishPotion THICK;
    public static final PlumDishPotion AWKWARD;
    public static final Map<ResourceLocation, PlumDishPotion> REGISTRY_MAP = Maps.newHashMap();
    public static final Map<Integer, PlumDishPotion> ID = Maps.newHashMap();
    private PlumDishPotions() {
    }

    public static boolean contains(String name) {
        return REGISTRY_MAP.containsKey(new ResourceLocation(name));
    }

    public static PlumDishPotion get(String name) {
        return REGISTRY_MAP.get(new ResourceLocation(name));
    }

    public static PlumDishPotion register(String name, int id, PlumDishPotion potion) {
        if (contains(name)) {
            throw new UnsupportedOperationException();
        } else {
            REGISTRY_MAP.put(new ResourceLocation(name), potion);
            ID.put(id, potion);
        }
        return potion;
    }

    static {
        EMPTY = register("empty", 0, new PlumDishPotion());
        WATER = register("water", 1, new PlumDishPotion());
        MUNDANE = register("mundane", 2, new PlumDishPotion());
        THICK = register("thick", 3, new PlumDishPotion());
        AWKWARD = register("awkward", 4, new PlumDishPotion());
    }
}
