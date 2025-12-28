
package com.bilibili.player_ix.blue_oceans.common.item;

import com.bilibili.player_ix.blue_oceans.init.GuiHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlueBookItem
extends Item {
    public BlueBookItem() {
        super(new Properties().fireResistant().stacksTo(1));
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            GuiHandler.openBlueBook(pPlayer);
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide);
    }
}
