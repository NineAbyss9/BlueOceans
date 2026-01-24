
package com.bilibili.player_ix.blue_oceans.common.blocks.ore;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;

public class UraniumOre
extends RadioactiveOre {
    public UraniumOre() {
        super(6.0F, 10.0F, SoundType.STONE, DyeColor.YELLOW, Element.U);
    }

    public float radioactiveLevel() {
        return 5F;
    }
}
