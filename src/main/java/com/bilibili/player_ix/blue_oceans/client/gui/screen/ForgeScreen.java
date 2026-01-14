
package com.bilibili.player_ix.blue_oceans.client.gui.screen;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.gui.menu.ForgeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ForgeScreen
extends ItemCombinerScreen<ForgeMenu> {
    static final ResourceLocation TEXTURE = BlueOceans.gui("forge");
    static final ResourceLocation ERROR = BlueOceans.gui("forge/error");
    public ForgeScreen(ForgeMenu pMenu, Inventory pPlayerInventory, Component pTitle,
                       ResourceLocation pMenuResource) {
        super(pMenu, pPlayerInventory, pTitle, pMenuResource);
    }

    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.translatable("gui.blue_oceans.forging"),
                pButton -> menu.increasePoundCount()).build());
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 0, 256, 256,
                256, 256);
        pGuiGraphics.drawString(this.font, Component.translatable("info.blue_oceans.pound_count",
                this.menu.getPoundCount()), 61, 20, 0);
    }

    protected void renderErrorIcon(GuiGraphics pGuiGraphics, int pX, int pY) {
        if (hasRecipeError()) {
            pGuiGraphics.blit(ERROR, 0, 0, 0, 0, 0, 0);
        }
    }

    private boolean hasRecipeError() {
        return this.menu.getSlot(0).hasItem() && this.menu.getSlot(1).hasItem() &&
                this.menu.getSlot(2).hasItem() && !this.menu.getSlot(this.menu.getResultSlot()).hasItem();
    }
}
