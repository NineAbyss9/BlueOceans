
package com.github.player_ix.ix_api.util;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class ResourceLocations {
    public ResourceLocations() {
    }

    @Nonnull
    public static ResourceLocation parse(String st) {
        return new ResourceLocation(st);
    }

    @Nonnull
    public static ResourceLocation fromNamespaceAndPath(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }
}
