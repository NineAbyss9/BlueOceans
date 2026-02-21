
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IShieldUser {
    boolean isUsingShield();

    void setUsingShield(boolean using);

    boolean isShieldOnCooldown();

    void disableShield(boolean pBecauseOfAxe);

    static void hurtShield(LivingEntity entity, int percent) {
        ItemStack stack = entity.getUseItem();
        int hurt = stack.getMaxDamage() / percent;
        stack.hurtAndBreak(hurt, entity, living -> living.broadcastBreakEvent(entity.getUsedItemHand()));
    }
}
