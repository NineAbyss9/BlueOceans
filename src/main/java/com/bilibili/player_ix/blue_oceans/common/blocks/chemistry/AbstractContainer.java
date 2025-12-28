
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import com.bilibili.player_ix.blue_oceans.api.misc.ContainerHolder;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.world.level.block.BaseEntityBlock;

public abstract class AbstractContainer
extends BaseEntityBlock {
    protected final ContainerHolder holder;
    public AbstractContainer(Properties pProperties) {
        super(pProperties);
        holder = new ContainerHolder();
    }

    public boolean fill(Content c) {
        return this.holder().fill(c);
    }

    public ContainerHolder holder() {
        return holder;
    }

    public int size() {
        return 1;
    }
}
