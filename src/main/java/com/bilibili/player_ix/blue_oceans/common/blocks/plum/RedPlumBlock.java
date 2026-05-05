
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.jetbrains.annotations.Nullable;
import org.NineAbyss9.math.MathSupport;

@SuppressWarnings("deprecation")
public class RedPlumBlock extends Block implements PlumBlock {
    public RedPlumBlock(Properties pProperties) {
        super(pProperties);
    }

    public RedPlumBlock() {
        this(Properties.of().strength(0.9F, 1F).mapColor(DyeColor.RED)
                .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.SCULK)
                .randomTicks());
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public boolean triggerEvent(BlockState pState, Level pLevel, BlockPos pPos, int pId, int pParam) {
        return false;
    }

    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return entity instanceof AbstractRedPlumMob ? 0.5F : 0.8F;
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.spawnPlum(pLevel, pPos, pRandom);
        if (pRandom.nextFloat() < 0.02F) {
            this.random25Action(pState, pLevel, pPos);
        }
    }

    protected void random25Action(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        if (this.getLevel() == 1) {
            pLevel.setBlock(pPos, MathSupport.random.nextBoolean() ?
                    BlueOceansBlocks.RED_PLUM_CATALYST.get().defaultBlockState() :
                    BlueOceansBlocks.SCLEROTIC_RED_PLUM_BLOCK.get().defaultBlockState(), 2);
        }
    }

    protected void spawnPlum(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
    }

    public Block getRestoreBlock(Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos.above()).isAir()) {
            return Blocks.GRASS_BLOCK;
        }
        return Blocks.STONE;
    }
}
