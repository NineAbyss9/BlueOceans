
package com.bilibili.player_ix.blue_oceans.client.gui.screen;

import com.bilibili.player_ix.blue_oceans.government.Government;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GovernmentScreen
extends Screen {
    private final Government government;
    public GovernmentScreen(Component pTitle, Government pGovernment) {
        super(pTitle);
        this.government = pGovernment;
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        //pGuiGraphics.drawString(Font);
    }
}
