
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import com.bilibili.player_ix.blue_oceans.api.misc.ContentHolder;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.world.level.block.BaseEntityBlock;

public abstract class AbstractContainer
extends BaseEntityBlock {
    protected final ContentHolder holder;
    public AbstractContainer(Properties pProperties) {
        super(pProperties);
        holder = new ContentHolder();
    }

    public boolean fill(Content c) {
        return this.holder().fill(c);
    }

    public ContentHolder holder() {
        return holder;
    }

    public int size() {
        return 1;
    }
}
