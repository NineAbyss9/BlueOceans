
package com.bilibili.player_ix.blue_oceans.init;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlueOceansGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> BO_EXPLODES_DESTROY_BLOCKS;
    public static final GameRules.Key<GameRules.BooleanValue> PLUM_SPREAD;

    static {
        BO_EXPLODES_DESTROY_BLOCKS = GameRules.register("bo_explodes_destroy_blocks",
                GameRules.Category.DROPS, GameRules.BooleanValue.create(true));
        PLUM_SPREAD = GameRules.register("plumSpread", GameRules.Category.UPDATES,
                GameRules.BooleanValue.create(true));
    }
}
