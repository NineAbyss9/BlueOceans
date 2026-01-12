
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.common.command.BoCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InitEvents {
    private InitEvents() {
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        BoCommand.register(event.getDispatcher(), event.getBuildContext());
    }
}
