
package com.bilibili.player_ix.blue_oceans.common.blocks.food;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.array.ObjectArray;
import org.NineAbyss9.util.ValueHolder;

@SuppressWarnings("deprecation")
public class Crop
extends CropBlock {
    private final int maxAge;
    private ItemLike baseSeed;
    private ObjectArray<VoxelShape> shapes;
    public Crop(Properties pProperties, int pMaxAge) {
        super(pProperties);
        this.maxAge = pMaxAge;
    }

    public Crop(Properties properties) {
        this(properties, 7);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        this.grow(pState, pLevel, pPos, pRandom);
    }

    public void grow(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int i = this.getAge(pState);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, pLevel, pPos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom
                        .nextInt((int)(25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, this.getGrowthState(pState, pLevel, pPos), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    public BlockState getGrowthState(BlockState pOriginState, ServerLevel pLevel, BlockPos pPos)
    {
        return this.getStateForAge(pOriginState.getValue(this.getAgeProperty()) + 1);
    }

    public Crop seed(ItemLike pBaseSeed) {
        this.baseSeed = pBaseSeed;
        return this;
    }

    public Crop shape(VoxelShape... pShape) {
        this.shapes = ObjectArray.of(pShape);
        return this;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return ValueHolder.nullToOther(shapes.get(this.getAge(pState)), super.getShape(pState, pLevel, pPos, pContext));
    }

    protected ItemLike getBaseSeedId() {
        return ValueHolder.nullToOther(baseSeed, super.getBaseSeedId());
    }

    public int getMaxAge() {
        return maxAge;
    }

    public IntegerProperty getAgeProperty()
    {
        return super.getAgeProperty();
    }
}
