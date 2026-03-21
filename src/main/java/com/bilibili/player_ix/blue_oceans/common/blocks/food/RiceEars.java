
package com.bilibili.player_ix.blue_oceans.common.blocks.food;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RiceEars
extends Crop {
    public static final IntegerProperty EARS_AGE = BlockStateProperties.AGE_1;
    public RiceEars(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return RiceBlock.SHAPE_BY_AGE.get(this.getAge(pState));
    }

    protected ItemLike getBaseSeedId() {
        return BlueOceansItems.RICE_SEEDS.get();
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(BlueOceansBlocks.RICE.get());
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return (pLevel.getRawBrightness(pPos, 0) >= 8 || pLevel.canSeeSky(pPos))
                && this.mayPlaceOn(pLevel.getBlockState(pPos.below()), pLevel, pPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(EARS_AGE);
    }

    public IntegerProperty getAgeProperty() {
        return EARS_AGE;
    }

    public int getMaxAge() {
        return 1;
    }
}
