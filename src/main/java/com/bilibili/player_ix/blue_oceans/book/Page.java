
package com.bilibili.player_ix.blue_oceans.book;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class Page {
    private final NonNullList<FormattedCharSequence> contents;
    private boolean isLocked;
    private boolean shouldRenderEntity;
    private LivingEntity renderEntity;
    public Page() {
        contents = NonNullList.withSize(127, pSink -> false);
    }

    public boolean shouldRenderEntity() {
        return shouldRenderEntity;
    }

    public LivingEntity getRenderEntity() {
        return renderEntity;
    }

    public void renderEntity(LivingEntity pRenderEntity) {
        shouldRenderEntity = true;
        renderEntity = pRenderEntity;
    }

    public void renderEntity(GuiGraphics guiGraphics) {

    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<FormattedCharSequence> getContents() {
        return contents;
    }

    public void addContent(FormattedCharSequence charSequence) {
        contents.add(charSequence);
    }
}
