
package com.github.player_ix.ix_api.api.item;

import com.github.player_ix.ix_api.util.ApiRandom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ItemStacks {
    private ItemStacks() {
    }

    public static ItemStack ofRanged(ItemLike like, int to) {
        return new ItemStack(like, ApiRandom.nextInt(to));
    }

    public static ItemStack of() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    public static ItemStack of(Item item) {
        return new ItemStack(item);
    }

    @Nonnull
    public static ItemStack of(@Nonnull Supplier<Item> item) {
        return of(item.get());
    }

    @Nonnull
    public static ItemStack of(Item item, int count) {
        return new ItemStack(item, count);
    }

    @Nonnull
    public static ItemStack of(@Nonnull Supplier<Item> item, int count) {
        return of(item.get(), count);
    }
}
