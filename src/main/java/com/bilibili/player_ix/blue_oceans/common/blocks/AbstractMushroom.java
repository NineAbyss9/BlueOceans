
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.nine_abyss.array.ObjectArray;

public abstract class AbstractMushroom
extends CropBlock {
    protected static final ObjectArray<VoxelShape> MUSHROOM_SHAPE;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public AbstractMushroom(Properties pProperties) {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return MUSHROOM_SHAPE.get(this.getAge(pState));
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return false;
    }

    public int getMaxAge() {
        return 3;
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    static {
        MUSHROOM_SHAPE = ObjectArray.withSize(3,
                box(1.0, 0.0, 1.0, 10.0, 10.0, 10.0),
                box(2.0, 0.0, 2.0, 11.0, 11.0, 11.0),
                box(4.0, 4.0, 4.0, 13.0, 13.0, 13.0));
    }
}
