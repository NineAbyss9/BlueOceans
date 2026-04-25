
package com.bilibili.player_ix.blue_oceans.common.blocks.nature;

import com.bilibili.player_ix.blue_oceans.common.blocks.core.AbstractMushroom;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SoundType;

//灵芝
public class GanodermaLucidum
extends AbstractMushroom
{
    public GanodermaLucidum() {
        super(Properties.of().noOcclusion().strength(2.0F, 10.0F)
                .sound(SoundType.GRASS).mapColor(DyeColor.BROWN).ignitedByLava());
    }

    protected ItemLike getBaseSeedId() {
        return BlueOceansItems.GANODERMA_LUCIDUM.get();
    }
}
