
package com.bilibili.player_ix.blue_oceans.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class PlumDishCommand {
    PlumDishCommand() {
    }

    public static void register(LiteralArgumentBuilder<CommandSourceStack> pDispatcher,
                                CommandBuildContext pContext) {
        pDispatcher.then(Commands.literal("plum_dish"));
    }
}
