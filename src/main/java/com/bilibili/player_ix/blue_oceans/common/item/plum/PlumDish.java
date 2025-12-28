
package com.bilibili.player_ix.blue_oceans.common.item.plum;

import com.bilibili.player_ix.blue_oceans.api.potion.PlumDishPotions;
import com.bilibili.player_ix.blue_oceans.util.PlumDishUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlumDish extends Item {
    public PlumDish() {
        super(new Properties().stacksTo(19).fireResistant());
    }

    public ItemStack getDefaultInstance() {
        return PlumDishUtil.setPotion(super.getDefaultInstance(), PlumDishPotions.WATER);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    public String getDescriptionId(ItemStack stack) {
        return PlumDishUtil.getPotion(stack).getName(this.getDescriptionId() + ".effect.");
    }

    public Rarity getRarity(ItemStack pStack) {
        return super.getRarity(pStack);
    }

    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTexts,
                                TooltipFlag pIsAdvanced) {
        PlumDishUtil.addText(pStack, pTexts);
    }
}
