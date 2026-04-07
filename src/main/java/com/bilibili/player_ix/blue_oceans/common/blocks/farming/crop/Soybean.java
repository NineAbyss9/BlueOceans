package com.bilibili.player_ix.blue_oceans.common.blocks.farming.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.array.ObjectArray;

public class Soybean extends CropBlock {
    public static final ObjectArray<VoxelShape> SHAPE_BY_AGE;

    public Soybean(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE.get(pState.getValue(this.getAgeProperty()));
    }

    public int getMaxAge() {
        return 3;
    }

    static {
        SHAPE_BY_AGE = ObjectArray.of(
                box(0, 0, 0, 16, 4, 16),
                box(0, 0, 0, 16, 8, 16),
                box(0, 0, 0, 16, 12, 16),
                box(0, 0, 0, 16, 16, 16)
        );
    }
}
