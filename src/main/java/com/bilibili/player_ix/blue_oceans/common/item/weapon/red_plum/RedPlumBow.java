
package com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Rarity;

public class RedPlumBow
extends BowItem {
    public RedPlumBow() {
        super(new Properties().durability(999)
                .rarity(Rarity.UNCOMMON).stacksTo(1));
    }
}
