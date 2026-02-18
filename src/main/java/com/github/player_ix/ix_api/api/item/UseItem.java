
package com.github.player_ix.ix_api.api.item;

import org.NineAbyss9.util.function.CiFunction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UseItem
extends Item {
    private final CiFunction<Level, Player, InteractionHand, InteractionResultHolder<ItemStack>> ciFunction;
    public UseItem(Properties pProperties,
                   CiFunction<Level, Player, InteractionHand, InteractionResultHolder<ItemStack>> pCiFunction) {
        super(pProperties);
        ciFunction = pCiFunction;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ciFunction.apply(pLevel, pPlayer, pUsedHand);
    }
}
