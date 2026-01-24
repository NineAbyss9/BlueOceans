
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

/**Terrible thing.*/
public class HydrogenBomb
extends AbstractBomb {
    public HydrogenBomb(Properties pProperties) {
        super(pProperties);
    }

    public float explodePower() {
        return 1000F;
    }
}
