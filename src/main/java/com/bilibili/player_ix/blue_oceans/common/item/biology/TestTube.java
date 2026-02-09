
package com.bilibili.player_ix.blue_oceans.common.item.biology;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TestTube extends Item {
    public static final String CONTENT_TAG = "Content";
    public TestTube(Properties pProperties) {
        super(pProperties);
    }

    public TestTube() {
        this(new Properties().stacksTo(1));
    }

    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        Content content = getContent(tag);
        if (tag != null && !content.isEmpty())
            pTooltipComponents.add(content.description());
    }

    public static Content getContent(@Nullable CompoundTag pTag) {
        if (pTag == null)
            return Content.EMPTY;
        return Content.of(pTag.getInt(CONTENT_TAG));
    }

    public static void setContent(CompoundTag pTag, int value) {
        pTag.putInt(CONTENT_TAG, value);
    }
}
