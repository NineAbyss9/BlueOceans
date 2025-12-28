
package com.bilibili.player_ix.blue_oceans.api.item;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

import javax.annotation.Nonnull;

public class BORarity {
    public static final Rarity RED_PLUM;

    @Nonnull
    private static Rarity create(String name, ChatFormatting chatFormatting) {
        return Rarity.create(name, chatFormatting);
    }

    static {
        RED_PLUM = create("RedPlum", ChatFormatting.DARK_RED);
    }
}
