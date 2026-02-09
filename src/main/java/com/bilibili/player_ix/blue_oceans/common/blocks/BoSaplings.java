
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class BoSaplings {
    public static AbstractTreeGrower orange() {
        return new OrangeTreeGrower();
    }

    public static class OrangeTreeGrower extends AbstractTreeGrower {
        protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
            return null;
        }
    }
}
