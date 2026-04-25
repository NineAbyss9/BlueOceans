
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
    public static final ResourceKey<DamageType> IMPORTANT_ORGAN_DEATH;
    public static final ResourceKey<DamageType> VIRUS_INVASION;
    public static ResourceKey<DamageType> damage(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, BlueOceans.location(name));
    }

    public static DamageSource damage(Level pLevel, @Nullable Entity pDirect, @Nullable Entity pEntity,
                                      @Nullable Vec3 pPos, ResourceKey<DamageType> pType) {
        return new DamageSource(pLevel.registryAccess().registry(Registries.DAMAGE_TYPE)
                .get().getHolder(pType).get(), pDirect, pEntity, pPos);
    }

    public static DamageSource clear(Level pLevel, @Nullable Entity pDirect, @Nullable Entity pEntity)
    {
        return damage(pLevel, pDirect, pEntity, null, CLEAR);
    }

    public static DamageSource importantOrganDeath(Level pLevel)
    {
        return damage(pLevel, null, null, null, IMPORTANT_ORGAN_DEATH);
    }

    public static DamageSource virusInvasion(Level pLevel) {
        return damage(pLevel, null, null, null, VIRUS_INVASION);
    }

    static {
        CLEAR = damage("clear");
        IMPORTANT_ORGAN_DEATH = damage("important_organ_death");
        VIRUS_INVASION = damage("virus_invasion");
    }
}
