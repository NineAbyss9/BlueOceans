
package com.bilibili.player_ix.blue_oceans.common.item.chemistry;

import com.bilibili.player_ix.blue_oceans.init.GuiHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PeriodicTableOfElementsItem
extends Item {
    public PeriodicTableOfElementsItem() {
        super(new Properties());
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            GuiHandler.openPeriodicTableOfElements();
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide);
    }
}
