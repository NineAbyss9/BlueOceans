
package com.bilibili.player_ix.blue_oceans.common.blocks.cooking;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@SuppressWarnings("deprecation")
public class RiceCooker
extends Block {
    public static final IntegerProperty AGE = BoBlockProperties.GROWTH_AGE;
    public RiceCooker(Properties pProperties) {
        super(pProperties);
    }

    public RiceCooker() {
        this(Properties.of().sound(SoundType.STONE).strength(2, 2));
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {

    }


}
