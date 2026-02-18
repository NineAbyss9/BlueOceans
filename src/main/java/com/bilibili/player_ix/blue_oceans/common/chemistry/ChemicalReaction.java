
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import net.minecraft.world.item.ItemStack;
import org.NineAbyss9.util.lister.Lister;

public record ChemicalReaction(Lister<ChemicalReaction> results, Lister<ItemStack> item, Condition condition) {
}
