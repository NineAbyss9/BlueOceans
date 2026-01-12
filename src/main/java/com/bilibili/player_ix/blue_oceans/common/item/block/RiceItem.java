
package com.bilibili.player_ix.blue_oceans.common.item.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RiceItem
extends ItemNameBlockItem {
    public RiceItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip,
                                TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("info.blue_oceans.rice_item"));
    }
}
