
package com.bilibili.player_ix.blue_oceans.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class BoCommand {
    private BoCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> pD, CommandBuildContext pC) {
        var bo = Commands.literal("blue_oceans")
                .requires(stack -> stack.hasPermission(2));
        //PlumDishCommand.register(bo, pC);
        SpawnerCommand.register(bo, pC);
        pD.register(bo);
    }
}
