
package com.github.player_ix.ix_api.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TipScreen
extends Screen {
    private ResourceLocation location;
    private int x;
    private int y;
    int blitOffset;
    float uOffset;
    float vOffset;
    int uWidth;
    int vHeight;
    int textureWidth;
    int textureHeight;
    public TipScreen(Component pTitle) {
        super(pTitle);
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.blit(this.location, x, y, blitOffset, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }
}
