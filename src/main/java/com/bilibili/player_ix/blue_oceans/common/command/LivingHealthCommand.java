
package com.bilibili.player_ix.blue_oceans.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

import static net.minecraft.commands.Commands.literal;

public class LivingHealthCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> pDispatcher,
                                CommandBuildContext pContext) {
        pDispatcher.then(literal("livingHealth"));
    }
}
