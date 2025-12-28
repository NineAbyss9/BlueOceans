
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.client.gui.screen.BlueBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class GuiHandler {
    public static void openPeriodicTableOfElements() {
        Minecraft.getInstance().setScreen(null);
    }

    public static void openBlueBook(Player pPlayer) {
        Minecraft.getInstance().setScreen(new BlueBookScreen(pPlayer));
    }
}
