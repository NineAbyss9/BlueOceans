
package com.bilibili.player_ix.blue_oceans.common.item.food;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class Orange
extends FoodItem {
    public Orange() {
        super(new Properties().stacksTo(64), (stack, pLevel, player) -> {
            player.addItem(new ItemStack(BlueOceansItems.ORANGE_FLESH.get()));
            stack.shrink(1);
            return stack;
        });
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    public int getUseDuration(ItemStack pStack) {
        return 20;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }
}
