
package com.github.player_ix.ix_api.init;

import com.github.player_ix.ix_api.commands.APICommand;
import com.github.player_ix.ix_api.network.APINetwork;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ApiInit {
    private ApiInit() {
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        APINetwork.register();
    }

    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        APICommand.register(commandDispatcher, event.getBuildContext());
    }
}
