
package com.bilibili.player_ix.blue_oceans.common.command;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEvents;
import com.bilibili.player_ix.blue_oceans.world.spawner.VillagerGroupSpawner;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static net.minecraft.commands.Commands.literal;

public class SpawnerCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> pDispatcher,
                                         CommandBuildContext pContext) {
        pDispatcher.then(literal("spawner").then(literal("villagerGroup"))
                .then(literal("spawnNow").executes(commandContext ->
                        spawnVillagerGroup(commandContext.getSource()))));
    }

    public static int spawnVillagerGroup(CommandSourceStack stack) {
        VillagerGroupSpawner spawner = BlueOceansEvents.VILLAGER_GROUPS.get(stack.getLevel());
        if (spawner != null && spawner.spawn(stack.getLevel())) {
            stack.sendSuccess(() -> Component.translatable("info.bo.command.villagerGroup.success"),
                    false);
            return 1;
        }
        return 0;
    }
}
