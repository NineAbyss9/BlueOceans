
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.init.data.ModBlockStateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings("deprecation")
public class Weed
extends BushBlock
implements BonemealableBlock, ModBlockStateProvider.Cross
{
    public static final IntegerProperty AGE;
    public Weed(Properties pProperties)
    {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(AGE);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (!pLevel.isAreaLoaded(pPos, 1) || pLevel.isLoaded(pPos)) return;
        if (pLevel.getRawBrightness(pPos, 0) >= 4) {
            int i = this.getAge(pState);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, pLevel, pPos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt(
                        (int)(25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, this.getStateForAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge()
    {
        return 2;
    }

    public int getAge(BlockState pState) {
        return pState.getValue(this.getAgeProperty());
    }

    public BlockState getStateForAge(int pAge) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(pAge));
    }

    public boolean isMaxAge(BlockState pState) {
        return this.getAge(pState) >= this.getMaxAge();
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return !this.isMaxAge(pState);
    }

    public float getDisableCropGrowthChance(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        return pState.getValue(AGE) * 0.2f + 0.05f;
    }

    protected float getGrowthSpeed(Block pBlock, BlockGetter pLevel, BlockPos pPos)
    {
        float f = 1.0F;
        BlockPos blockpos = pPos.below();
        for (int i = -1;i <= 1;++i) {
            for (int j = -1;j <= 1;++j) {
                float f1 = 0.0F;
                BlockState blockstate = pLevel.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(pLevel, blockpos.offset(i, 0, j), Direction.UP,
                        (net.minecraftforge.common.IPlantable)pBlock)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(pLevel, pPos.offset(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }
                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }
                f += f1;
            }
        }
        BlockPos blockpos1 = pPos.north();
        BlockPos blockpos2 = pPos.south();
        BlockPos blockpos3 = pPos.west();
        BlockPos blockpos4 = pPos.east();
        boolean flag = pLevel.getBlockState(blockpos3).is(pBlock) || pLevel.getBlockState(blockpos4).is(pBlock);
        boolean flag1 = pLevel.getBlockState(blockpos1).is(pBlock) || pLevel.getBlockState(blockpos2).is(pBlock);
        if (flag && flag1) {
            f /= 1.5F;
        } else {
            boolean flag2 = pLevel.getBlockState(blockpos3.north()).is(pBlock) || pLevel.getBlockState(blockpos4.north()).is(pBlock) ||
                    pLevel.getBlockState(blockpos4.south()).is(pBlock) || pLevel.getBlockState(blockpos3.south()).is(pBlock);
            if (flag2) {
                f /= 1.25F;
            }
        }
        return f;
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState)
    {
        return false;
    }

    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient)
    {
        return false;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState)
    {
        BlockState blockstate = this.defaultBlockState();
        //BlockState blockstate1 = blockstate.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos blockpos = pPos.above();
        if (pLevel.getBlockState(blockpos).isAir()) {
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.setBlock(blockpos, blockstate//1
                    , 2);
        }
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity)
    {
        if (!pEntity.getType().is(BoTags.HEAVY_MOBS) || !ForgeEventFactory.getMobGriefingEvent(pLevel, pEntity)
        || pState.getValue(AGE) == 0) {
            return;
        }
        pLevel.destroyBlock(pPos, true);
        pLevel.setBlock(pPos, this.getStateForAge(0), 2);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        if (!pLevel.canSeeSky(pPos)) return false;
        if (!pState.canSustainPlant(pLevel, pPos, Direction.UP, this)) return false;
        return super.canSurvive(pState, pLevel, pPos);
    }

    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable)
    {
        return plantable instanceof Weed;
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player)
    {
        return new ItemStack(this.asItem());
    }

    static {
        AGE = BlockStateProperties.AGE_2;
    }
}
