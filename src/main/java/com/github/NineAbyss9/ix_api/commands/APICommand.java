
package com.github.NineAbyss9.ix_api.commands;

import com.github.NineAbyss9.ix_api.util.IXList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.List;

public class APICommand {
    public APICommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext p_250122_) {
        dispatcher.register(Commands.literal("ix_api")
                .requires((stack) -> stack.hasPermission(2))
                .then(Commands.literal("apiRemover")
                        .then(Commands.literal("remove")
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .then(Commands.argument("removeReason", IntegerArgumentType.integer())
                                                .executes(commandContext ->
                                                        CommandRemover.remove(commandContext.getSource(),
                                                                EntityArgument.getEntities(commandContext, "targets"),
                                                                IntegerArgumentType.getInteger(commandContext, "removeReason"))))))
                        .then(Commands.literal("setRemoved")
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .then(Commands.argument("removeReason", IntegerArgumentType.integer())
                                                .executes(commandContext ->
                                                        CommandRemover.setRemoved(commandContext.getSource(),
                                                                EntityArgument.getEntities(commandContext, "targets"),
                                                                IntegerArgumentType.getInteger(commandContext, "removeReason"))
                                                ))))
                        .then(Commands.literal("onRemovedFromWorld")
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(commandContext ->
                                                CommandRemover.onRemovedFromWorld(commandContext.getSource(),
                                                        EntityArgument.getEntities(commandContext, "targets"))))))
                .then(Commands.literal("maths")
                        .then(Commands.literal("randomFloat")
                                .executes(commandContext -> nextFloat(commandContext.getSource())))
                        .then(Commands.literal("randomDouble")
                                .executes(commandContext -> nextDouble(commandContext.getSource())))
                        .then(Commands.literal("randomInteger")
                                .executes(commandContext -> nextInt(commandContext.getSource())))
                        .then(Commands.literal("nextBoolean")
                                .executes(commandContext -> nextBoolean(commandContext.getSource()))))
                .then(Commands.literal("apiCrusher")
                        .then(Commands.literal("throwNewRuntimeException")
                                .executes(commandContext -> throwNew())))
                .then(Commands.literal("potionCreate")
                        .then(Commands.argument("potion", ResourceArgument.resource(p_250122_, Registries.POTION))
                                .executes(commandContext -> potion(commandContext.getSource(),
                                        ResourceArgument.getResource(commandContext, "potion", Registries.POTION)))))
                .then(Commands.literal("potionCreateWithMobEffect")
                        .then(Commands.argument("potion", ResourceArgument.resource(p_250122_, Registries.MOB_EFFECT))
                                .then(Commands.argument("type", IntegerArgumentType.integer(0, 2))
                                        .then(Commands.argument("time", IntegerArgumentType.integer(-1))
                                                .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                                        .executes(commandContext -> mobEffect(commandContext.getSource(),
                                                                IntegerArgumentType.getInteger(commandContext, "type"),
                                                                ResourceArgument.getResource(commandContext, "potion", Registries.MOB_EFFECT),
                                                                IntegerArgumentType.getInteger(commandContext, "time"),
                                                                IntegerArgumentType.getInteger(commandContext, "level"))))))))
                .then(Commands.literal("enchanter")
                        .then(Commands.argument("type", ResourceArgument.resource(p_250122_,
                                Registries.ENCHANTMENT))
                                .then(Commands.argument("level", IntegerArgumentType.integer(0,
                                        Integer.MAX_VALUE)).executes(commandContext -> {
                                    CommandEnchanter.enchant(commandContext.getSource(), ResourceArgument.getResource(
                                            commandContext, "type", Registries.ENCHANTMENT), IntegerArgumentType.getInteger(
                                            commandContext, "level"));
                                    return 1;
                                })))));
    }

    private static int throwNew() {
        Minecraft.crash(new CrashReport("As you requested.", new RuntimeException()));
        throw new RuntimeException("As you requested.");
    }

    private static int nextBoolean(CommandSourceStack stack) {
        return CommandMaths.nextBoolean(stack);
    }

    private static int nextFloat(CommandSourceStack stack) {
        return CommandMaths.nextFloat(stack);
    }

    private static int nextDouble(CommandSourceStack stack) {
        return CommandMaths.nextDouble(stack);
    }

    private static int nextInt(CommandSourceStack stack) {
        return CommandMaths.nextInt(stack);
    }

    private static int potion(CommandSourceStack stack, Holder.Reference<Potion> path) {
        Player player = stack.getPlayer();
        if (player != null) {
            ItemStack itemStack = new ItemStack(Items.POTION, 1);
            PotionUtils.setPotion(itemStack, path.get());
            if (player.addItem(itemStack)) {
                stack.sendSuccess(()-> Component.translatable("command.noixapi.potion.success",
                        player.getDisplayName()), true);
            }
        }
        return 1;
    }

    private static int mobEffect(CommandSourceStack stack, Integer name, Holder<MobEffect> path,
                                 int time, int level) {
        Player player = stack.getPlayer();
        if (player != null) {
            Item item;
            if (name == 0) {
                item = Items.POTION;
            } else if (name == 1) {
                item = Items.SPLASH_POTION;
            } else {
                item = Items.LINGERING_POTION;
            }
            ItemStack itemStack = new ItemStack(item, 1);
            List<MobEffectInstance> ixList = IXList.of();
            ixList.add(new MobEffectInstance(path.get(), time, level));
            PotionUtils.setCustomEffects(itemStack, ixList);
            player.addItem(itemStack);
        }
        return 1;
    }
}
