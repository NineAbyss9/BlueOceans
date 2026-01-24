
package com.bilibili.player_ix.blue_oceans.common.blocks.ore;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;

public class Bauxite
extends AbstractElementOre {
    public Bauxite() {
        super(4F, 60F, SoundType.STONE,
                DyeColor.GRAY, Element.Al);
    }
}
