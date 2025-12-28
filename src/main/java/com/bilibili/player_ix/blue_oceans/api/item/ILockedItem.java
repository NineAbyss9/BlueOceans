
package com.bilibili.player_ix.blue_oceans.api.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface ILockedItem {
    default MutableComponent description() {
        return Component.translatable("info.blue_oceans.locked");
    }

    default boolean isLocked() {
        return true;
    }
}
