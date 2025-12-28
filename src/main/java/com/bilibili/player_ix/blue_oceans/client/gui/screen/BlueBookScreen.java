
package com.bilibili.player_ix.blue_oceans.client.gui.screen;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.book.BlueBook;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BlueBookScreen
extends Screen {
    public static final ResourceLocation TEXTURE = BlueOceans.gui("blue_book/blue_book");
    public final BlueBook blueBook;
    private final int bookWidth = 146;
    private final int bookHeight = 180;
    private int currentPage;
    private int leftPos;
    private int topPos;
    public BlueBookScreen(Player pPlayer) {
        super(Component.translatable("gui.blue_oceans.blue_book"));
        blueBook = new BlueBook();
    }

    protected void init() {
        super.init();
        this.leftPos = (this.width - this.bookWidth) / 2;
        this.topPos = (this.height - this.bookHeight) / 2;
        // 添加翻页按钮
        //20, 20;
        //20, 20;
        this.addRenderableWidget(Button.builder(Component.literal("<"), button -> previousPage())
                .pos(this.leftPos + 12, this.topPos + 156).width(20).build());
        this.addRenderableWidget(Button.builder(Component.literal(">"), button -> nextPage())
                .pos(this.leftPos + 114, this.topPos + 156).width(20).build());
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        // 绘制书本背景
        pGuiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, bookWidth, bookHeight);
        // 绘制页码
        pGuiGraphics.drawString(this.font, (currentPage + 1) + "/" + this.getPageCount(),
                leftPos + bookWidth / 2 - 5, topPos + 156, 0x000000, false);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public int getPageCount() {
        return 114;
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
        }
    }

    public void nextPage() {
        if (currentPage < this.getPageCount())
            currentPage++;
    }

    public boolean isPauseScreen() {
        return false;
    }
}
