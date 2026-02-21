
package com.bilibili.player_ix.blue_oceans.common.blocks.farming.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.array.ObjectArray;

public class Cotton
extends CropBlock {
    public static final ObjectArray<VoxelShape> SHAPE_BY_AGE;
    public Cotton(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE.get(pState.getValue(this.getAgeProperty()));
    }

    public int getMaxAge() {
        return 5;
    }

    static {
        SHAPE_BY_AGE = ObjectArray.of(box(0, 0, 0, 16, 2, 16),
                box(0, 0, 0, 16, 3, 16),
                box(0, 0, 0, 16, 6, 16),
                box(0, 0, 0, 16, 32, 16),
                box(0, 0, 0, 16, 32, 16));
    }
}
