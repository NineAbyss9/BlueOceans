
package com.github.player_ix.ix_api.api.mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nonnull;

public interface IProjectile {
    void onHit(HitResult result);

    void onHitEntity(EntityHitResult p_37259_);

    void onHitBlock(BlockHitResult p_37258_);

    default boolean canHitEntity(@Nonnull Entity p_37250_) {
        return true;
    }
}
