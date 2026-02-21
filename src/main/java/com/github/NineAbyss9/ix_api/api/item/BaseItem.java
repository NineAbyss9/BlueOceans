
package com.github.NineAbyss9.ix_api.api.item;

import net.minecraft.world.item.Item;

public class BaseItem
extends Item {
    public BaseItem(Properties pProperties) {
        super(pProperties);
    }

    public BaseItem(int pMaxStackSize) {
        this(new Properties().stacksTo(pMaxStackSize));
    }

    public BaseItem() {
        this(64);
    }
}
