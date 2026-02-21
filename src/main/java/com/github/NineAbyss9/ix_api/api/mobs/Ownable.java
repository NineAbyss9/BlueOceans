
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.monster.Enemy;

import javax.annotation.Nullable;
import java.util.UUID;

public interface Ownable
extends TraceableEntity, OwnableEntity {
    default boolean isHostile() {
        return this.getOwner() instanceof Enemy;
    }

    @Nullable
    default LivingEntity getMasterOwner() {
        if (this.getOwner() instanceof Ownable ownable) {
            return ownable.getOwner();
        } else {
            return this.getOwner();
        }
    }

    @Nullable
    LivingEntity getOwner();

    @Nullable
    UUID getOwnerUUID();

    void setOwner(@Nullable LivingEntity lie);

    void setOwnerUUID(UUID ownerUUID);

    default void setTargetByOwner() {
        if (this instanceof Mob && this.getOwner() instanceof Mob) {
            OwnableMob.setTargetByOwner(this);
        }
    }

    default void moveToOwner(boolean flag) {
        if (this instanceof Mob && this.getOwner() != null) {
            OwnableMob.moveToOwner(this, flag);
        }
    }

    default void setLifeTicks(int tick) {
    }

    default int getLifeTicks() {
        return 0;
    }

    default boolean hasLife() {
        return false;
    }

    default boolean isFlyable() {
        return false;
    }

    default boolean wouldHaveOwner() {
        return true;
    }

    default boolean isUnowned() {
        return this.getOwner() == null;
    }

    default boolean areBothOwner(LivingEntity living) {
        if (living instanceof Ownable ownable) {
            return ownable.getOwner() == this && this.getOwner() == living;
        } else {
            return false;
        }
    }

    default void addOwnableAdditionalSaveData(CompoundTag tag) {
        if (this.getOwnerUUID() != null) {
            tag.putUUID("OwnerUUID", this.getOwnerUUID());
        }
        if (this.getOwner() != null) {
            tag.putString("Owner", this.getOwner().getDisplayName().getString());
        }
    }

    default void readOwnableAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID("OwnerUUID")) {
            this.setOwnerUUID(tag.getUUID("OwnerUUID"));
        }
    }
}
