
package com.bilibili.player_ix.blue_oceans.common.item.biology.organ;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface Organ {
    String POWER_TAG = "Power";
    float getPower(LivingEntity pEntity);

    default float getPower(ItemStack pStack) {
        return pStack.getOrCreateTag().getFloat(POWER_TAG);
    }

    default void beforeDie(ServerLevel pLevel, LivingEntity pEntity) {}

    default void randomTick(Level pLevel, LivingEntity pEntity) {}
}
