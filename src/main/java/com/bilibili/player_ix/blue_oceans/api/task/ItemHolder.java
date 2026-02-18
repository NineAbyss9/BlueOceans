
package com.bilibili.player_ix.blue_oceans.api.task;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.NineAbyss9.util.IXUtil;
import org.NineAbyss9.util.IXUtilUser;

@FunctionalInterface
public interface ItemHolder extends IXUtilUser {
    Object getTaskItem();

    private <T> T convert(Object obj) {
        return IXUtil.c.convert(obj);
    }

    default ItemStack getItemStack() {
        return this.convert(getTaskItem());
    }

    default Item getItem() {
        return this.convert(getTaskItem());
    }

    default boolean isEmpty() {
        return this.getTaskItem() == null;
    }

    default boolean sameStack(ItemStack pStack) {
        if (isEmpty())
            return false;
        return pStack.equals(this.getItemStack(), true);
    }

    default boolean sameItem(Item pItem) {
        if (isEmpty())
            return false;
        return pItem.equals(this.getItem());
    }
}
