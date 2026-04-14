
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.NineAbyss9.math.MathSupport;

import java.util.random.RandomGenerator;

public interface Organ
{
    String POWER_TAG = "Power";
    float getPower(LivingEntity pEntity);

    default float getPower(CompoundTag pTag) {
        return pTag.getFloat(POWER_TAG);
    }

    default void takeDamage(float damage) {}

    default void damageOwner(LivingEntity pEntity) {}

    default void applyBuff(LivingEntity pEntity) {}

    default void onRemove(LivingEntity pEntity) {}

    default void beforeDie(ServerLevel pLevel, LivingEntity pEntity) {}

    default void randomTick(Level pLevel, LivingEntity pEntity) {
        this.randomTick(pLevel, pEntity, MathSupport.random);
    }

    default void randomTick(Level pLevel, LivingEntity pEntity, RandomGenerator randomGeneratorIn) {}
}
