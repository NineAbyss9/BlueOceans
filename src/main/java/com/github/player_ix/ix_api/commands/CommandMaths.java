
package com.github.player_ix.ix_api.commands;

import com.github.player_ix.ix_api.util.Maths;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class CommandMaths {
    public CommandMaths() {
    }

    public static int nextBoolean(CommandSourceStack stack) {
        stack.sendSuccess(()-> Component.literal(String.valueOf(Maths.random.nextBoolean())), true);
        return 1;
    }

    public static int nextFloat(CommandSourceStack stack) {
        stack.sendSuccess(()->Component.literal(String.valueOf(Maths.random.nextFloat())), true);
        return 1;
    }

    public static int nextDouble(CommandSourceStack stack) {
        stack.sendSuccess(()->Component.literal(String.valueOf(Maths.random.nextDouble())), true);
        return 1;
    }

    public static int nextInt(CommandSourceStack stack) {
        stack.sendSuccess(()->Component.literal(String.valueOf(Maths.random.nextInt())), true);
        return 1;
    }
}
