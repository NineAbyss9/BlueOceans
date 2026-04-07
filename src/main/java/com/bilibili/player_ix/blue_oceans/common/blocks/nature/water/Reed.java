
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.water;

import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.init.data.ModBlockStateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.NineAbyss9.array.ObjectArray;

@SuppressWarnings("deprecation")
public class Reed
extends AquaticPlant
implements ModBlockStateProvider.Cross {
    public static final IntegerProperty REED_AGE;
    private static final ObjectArray<VoxelShape> SHAPES;
    public Reed(Properties pProperties)
    {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(REED_AGE);
    }

    /*public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;
        BlockPos above = pPos.above();
        if (pLevel.getRawBrightness(above, 0) >= 6) {
            int currentAge = this.getAge(pState);
            if (currentAge <= this.getMaxAge()) {
                if (ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextFloat() < 0.3f)) {
                    if (currentAge == this.getMaxAge()) {
                        // TOD: Replace
                        RiceEars ears = BlueOceansBlocks.RICE_EARS.get();
                        if (ears.defaultBlockState().canSurvive(pLevel, above) && pLevel.isEmptyBlock(above)) {
                            pLevel.setBlockAndUpdate(above, ears.defaultBlockState());
                            ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                        }
                    } else {
                        pLevel.setBlock(pPos, this.defaultBlockState().setValue(
                                this.getAgeProperty(), currentAge + 1), 2);
                        ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                    }
                }
            }
        }
    }*/

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHAPES.get(this.getAge(pState));
    }

    protected ItemLike getBaseSeedId()
    {
        return this.asItem();
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit)
    {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.is(BoTags.SHEARS)) {
            if (!pLevel.isClientSide) {
                dropResources(pState, pLevel, pPos);
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    protected IntegerProperty getAgeProperty()
    {
        return REED_AGE;
    }

    static {
        REED_AGE = BlockStateProperties.AGE_2;
        SHAPES = ObjectArray.of(box(8, 8, 8, 8, 8, 8),
                box(8, 0, 8, 8, 16, 8));
    }
}
