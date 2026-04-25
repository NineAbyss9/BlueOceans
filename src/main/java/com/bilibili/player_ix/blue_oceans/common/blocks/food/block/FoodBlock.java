
package com.bilibili.player_ix.blue_oceans.common.blocks.food.block;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.github.NineAbyss9.ix_api.api.mobs.FoodDataUser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.array.ObjectArray;
import org.NineAbyss9.util.function.FunctionCollector;

@SuppressWarnings("deprecation")
public class FoodBlock
extends Block {
    protected static final IntegerProperty BITES = BoBlockProperties.BITES;
    private final int maxEatingCount;
    private final ObjectArray<VoxelShape> shape;
    public FoodBlock(Properties pProperties, int maxEatingCountIn, ObjectArray<VoxelShape> shapes) {
        super(pProperties);
        this.maxEatingCount = maxEatingCountIn;
        this.shape = shapes;
    }

    public FoodBlock(Properties pProperties) {
        this(pProperties, 3, (ObjectArray<VoxelShape>)FunctionCollector.get(() -> {
            ObjectArray<VoxelShape> array = ObjectArray.<VoxelShape>withSize(10);
            for (int i = 0;i < array.length();i++) {
                array.set(i, Shapes.block());
            }
            return array;
        }));
    }

    public FoodBlock(Properties pProperties, int maxEatingCountIn) {
        this(pProperties, maxEatingCountIn, (ObjectArray<VoxelShape>)FunctionCollector.get(() -> {
            ObjectArray<VoxelShape> array = ObjectArray.<VoxelShape>withSize(10);
            for (int i = 0;i < array.length();i++) {
                array.set(i, Shapes.block());
            }
            return array;
        }));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(this.getBites());
    }

    public IntegerProperty getBites() {
        return BITES;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shape.get(pState.getValue(this.getBites()));
    }

    public BlockState setBites(BlockState pState, int bites) {
        return pState.setValue(this.getBites(), bites);
    }

    public BlockState increaseBites(BlockState pState, int bites) {
        return pState.setValue(this.getBites(), pState.getValue(this.getBites()) + bites);
    }

    public int getMaxEatingCount() {
        return this.maxEatingCount;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        ItemStack $$6 = pPlayer.getItemInHand(pHand);
        /*Item $$7 = $$6.getItem();
        if ($$6.is(ItemTags.CANDLES) && (Integer)pState.getValue(BITES) == 0) {
            Block $$8 = Block.byItem($$7);
            if ($$8 instanceof CandleBlock) {
                ItemUtil.shrink($$6, pPlayer);
                pLevel.playSound((Player)null, pPos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F,
                        1.0F);
                pLevel.setBlockAndUpdate(pPos, CandleCakeBlock.byCandle($$8));
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos);
                pPlayer.awardStat(Stats.ITEM_USED.get($$7));
                return InteractionResult.SUCCESS;
            }
        }*/
        if (pLevel.isClientSide) {
            if (eat(pLevel, pPos, pState, pPlayer).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            if ($$6.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return eat(pLevel, pPos, pState, pPlayer);
    }

    protected InteractionResult eat(LevelAccessor pLevel, BlockPos pPos, BlockState pState, LivingEntity player) {
        if (player instanceof FoodDataUser user) {
            var item = Item.byBlock(pState.getBlock());
            user.foodData().eat(item, item.getDefaultInstance(), player);
            return InteractionResult.SUCCESS;
        } else if (player instanceof Player pPlayer) {
            if (!pPlayer.canEat(false)) {
                return InteractionResult.PASS;
            } else {
                pPlayer.awardStat(Stats.EAT_CAKE_SLICE);
                pPlayer.getFoodData().eat(2, 0.1F);
                int $$4 = (Integer)pState.getValue(BITES);
                pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
                if ($$4 < 6) {
                    pLevel.setBlock(pPos, (BlockState)pState.setValue(BITES, $$4 + 1), 3);
                } else {
                    pLevel.removeBlock(pPos, false);
                    pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel,
                                  BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() :
                super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolid();
    }

    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return getOutputSignal((Integer)pBlockState.getValue(BITES));
    }

    public static int getOutputSignal(int pEaten) {
        return (7 - pEaten) * 2;
    }

    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
