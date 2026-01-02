
package com.bilibili.player_ix.blue_oceans.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.Mob;

public class EntityInfoScreen
extends Screen {
    private final Mob entity;
    protected int leftPos;
    protected int topPos;
    private float xMouse;
    private float yMouse;
    public EntityInfoScreen(Mob pMob) {
        super(pMob.getDisplayName());
        this.entity = pMob;
    }

    protected void init() {
        super.init();
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawString(font, entity.getDisplayName(), 0, 0, 0);
    }

    @SuppressWarnings("all")
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, i + 51, j + 75, 30,
                i + 51 - this.xMouse, j + 75 - 50 - this.yMouse, this.minecraft.player);
    }
}
