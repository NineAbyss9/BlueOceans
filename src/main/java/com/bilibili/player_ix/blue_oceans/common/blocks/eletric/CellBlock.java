
package com.bilibili.player_ix.blue_oceans.common.blocks.eletric;

import com.bilibili.player_ix.blue_oceans.common.physic.Electric;
import net.minecraft.world.level.block.Block;

public class CellBlock
extends Block
implements Electric {
    private float volt;
    private float a;
    public CellBlock(Properties pProperties) {
        super(pProperties);
    }

    public float getVolt() {
        return volt;
    }

    public float getA() {
        return a;
    }

    public void setVolt(float volt) {
        this.volt = volt;
    }

    public void setA(float a) {
        this.a = a;
    }
}
