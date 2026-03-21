
package com.bilibili.player_ix.blue_oceans.common.blocks.biology;

import net.minecraft.world.level.block.Block;

public class Microscope
extends Block
{
    public float zoom;
    public Microscope(Properties pProperties, float baseZoom)
    {
        super(pProperties);
        this.zoom = baseZoom;
    }


}
