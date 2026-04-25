
package com.bilibili.player_ix.blue_oceans.common.item.cooking;

import com.bilibili.player_ix.blue_oceans.init.data.ModItemModelProvider;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;

public class Sorbet
extends TieredItem
implements ModItemModelProvider.Handed {
    public Sorbet(Properties pProperties) {
        super(Tiers.IRON, pProperties);
    }
}
