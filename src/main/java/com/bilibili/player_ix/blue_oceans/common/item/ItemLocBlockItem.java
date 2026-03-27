
package com.bilibili.player_ix.blue_oceans.common.item;

import com.bilibili.player_ix.blue_oceans.init.data.ITextureProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class ItemLocBlockItem
extends BlockItem
implements ITextureProvider
{
    private final String address;
    public ItemLocBlockItem(Block pBlock, Properties pProperties, String addressIn)
    {
        super(pBlock, pProperties);
        this.address = addressIn;
    }

    public String getAddress()
    {
        return address;
    }
}
