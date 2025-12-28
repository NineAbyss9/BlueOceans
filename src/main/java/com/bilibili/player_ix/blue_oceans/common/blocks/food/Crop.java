
package com.bilibili.player_ix.blue_oceans.common.blocks.food;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.nine_abyss.util.ValueHolder;

public class Crop
extends CropBlock {
    private final int maxAge;
    private ItemLike baseSeed;
    private VoxelShape shape;
    public Crop(Properties pProperties, int pMaxAge) {
        super(pProperties);
        this.maxAge = pMaxAge;
    }

    public Crop(Properties properties) {
        this(properties, 7);
    }

    public Crop seed(ItemLike pBaseSeed) {
        this.baseSeed = pBaseSeed;
        return this;
    }

    public Crop shape(VoxelShape pShape) {
        this.shape = pShape;
        return this;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return ValueHolder.nullToOther(shape, super.getShape(pState, pLevel, pPos, pContext));
    }

    protected ItemLike getBaseSeedId() {
        return ValueHolder.nullToOther(baseSeed, super.getBaseSeedId());
    }

    public int getMaxAge() {
        return maxAge;
    }
}
