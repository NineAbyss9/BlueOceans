
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.common.command.BoCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InitEvents {
    private InitEvents() {
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        BoCommand.register(event.getDispatcher(), event.getBuildContext());
    }

    //Capabilities start

    public static void registerEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity living) {
            org.nine_abyss.util.Nothing.getInstance().noting();
        }
    }
}
