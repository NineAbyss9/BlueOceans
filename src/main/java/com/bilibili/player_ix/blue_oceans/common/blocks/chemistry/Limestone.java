
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

//CaCO3 石灰石
@SuppressWarnings("deprecation")
public class Limestone
extends Block {
    public Limestone() {
        super(Properties.of().strength(1.7F, 1.7F)
                .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().mapColor(MapColor.STONE));
    }
}
