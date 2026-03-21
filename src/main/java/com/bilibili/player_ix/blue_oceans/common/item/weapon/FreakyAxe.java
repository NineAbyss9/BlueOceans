
package com.bilibili.player_ix.blue_oceans.common.item.weapon;

import com.bilibili.player_ix.blue_oceans.init.data.ModItemModelProvider;
import com.github.NineAbyss9.ix_api.util.ItemUtil;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

//May be deprecated
public class FreakyAxe
extends AxeItem
implements ModItemModelProvider.Handed
{
    private static final Tier TIER = ItemUtil.getTier(0,
            4F, 10f, 3, 12, Ingredient.EMPTY);
    public FreakyAxe() {
        super(TIER, 1.0f, -2.8f, new Item.Properties());
    }
}
