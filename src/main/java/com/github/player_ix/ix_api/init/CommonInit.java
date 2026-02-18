
package com.github.player_ix.ix_api.init;

import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import com.github.player_ix.ix_api.commands.APICommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;

@PAMAreNonnullByDefault
public class CommonInit {
    private CommonInit() {
    }

    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        APICommand.register(commandDispatcher, event.getBuildContext());
    }
}
