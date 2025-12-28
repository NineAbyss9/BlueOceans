
package com.bilibili.player_ix.blue_oceans.common.item.biology;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

public class TestTube extends Item {
    public static final String CONTENT_TAG = "Content";
    public TestTube() {
        super(new Properties().stacksTo(1));
    }

    public static Content getContent(CompoundTag pTag) {
        return Content.of(pTag.getInt(CONTENT_TAG));
    }

    public static void setContent(CompoundTag pTag, int value) {
        pTag.putInt(CONTENT_TAG, value);
    }
}
