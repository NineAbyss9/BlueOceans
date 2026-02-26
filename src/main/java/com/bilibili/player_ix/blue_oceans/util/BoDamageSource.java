
package com.bilibili.player_ix.blue_oceans.util;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BoDamageSource {
    public static final ResourceKey<DamageType> CLEAR;
    public static ResourceKey<DamageType> damage(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, BlueOceans.location(name));
    }

    public static DamageSource damage(Level pLevel, @Nullable Entity pDirect, @Nullable Entity pEntity,
                                      @Nullable Vec3 pPos, ResourceKey<DamageType> pType) {
        return new DamageSource(pLevel.registryAccess().registry(Registries.DAMAGE_TYPE)
                .get().getHolder(pType).get(), pDirect, pEntity, pPos);
    }

    public static DamageSource clear(Level pLevel, @Nullable Entity pDirect, @Nullable Entity pEntity) {
        return damage(pLevel, pDirect, pEntity, null, CLEAR);
    }

    static {
        CLEAR = damage("clear");
    }
}
