
package com.bilibili.player_ix.blue_oceans.common.item.plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
//import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.world.item.BlockItem;
//import net.minecraft.world.item.ItemStack;

public class PlumTissue
extends BlockItem {
    public PlumTissue() {
        super(BlueOceansBlocks.PLUM_TISSUE.get(), new Properties().stacksTo(64));
    }

    /*public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack cache = itemStack.copy();
        if (cache.getDamageValue() < cache.getMaxDamage())
            cache.setDamageValue(cache.getDamageValue() + 1);
        else
            return ItemStack.EMPTY;
        return cache;
    }

    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(BlueOceansItems.PLUM_CELL_CLUSTER.get());
    }*/
}
