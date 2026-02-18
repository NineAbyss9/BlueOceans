
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoPlum;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class PlumTissueBlock
extends Block {
    static final VoxelShape SHAPE;
    public PlumTissueBlock() {
        super(Properties.of().randomTicks().strength(0.2F).mapColor(DyeColor.RED)
                .sound(SoundType.SCULK));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.1F) {
            var plum = NeoPlum.createRandom(pPos, pLevel);
            if (plum != null)
                NeoPlum.addParticleAroundPlum(plum);
            pLevel.destroyBlock(pPos, false);
        }
    }

    static {
        SHAPE = Block.box(6, 0, 6, 10, 2, 10);
    }
}
