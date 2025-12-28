
package com.github.player_ix.ix_api.commands;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

public class CommandEnchanter {
    public CommandEnchanter() {
    }

    public static void enchant(CommandSourceStack stack, Holder<Enchantment> location, int level) {
        Entity entity = stack.getEntity();
        if (entity instanceof LivingEntity living) {
            Enchantment enchantment = location.get();
            living.getMainHandItem().enchant(enchantment, level);
        }
    }
}
