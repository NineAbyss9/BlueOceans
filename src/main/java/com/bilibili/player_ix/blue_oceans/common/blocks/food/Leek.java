
package com.bilibili.player_ix.blue_oceans.common.blocks.food;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.nine_abyss.array.ObjectArray;

@SuppressWarnings("deprecation")
public class Leek
extends CropBlock {
    static final ObjectArray<VoxelShape> SHAPE;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public Leek() {
        super(Properties.copy(Blocks.WHEAT));
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.is(BoTags.SHEARS) && this.isMaxAge(pState)) {
            itemStack.hurtAndBreak(1, pPlayer, player -> player.broadcastBreakEvent(pHand));
            dropResources(pState, pLevel, pPos);
            pLevel.setBlock(pPos, this.getStateForAge(1), 2);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE.get(this.getAge(pState));
    }

    protected ItemLike getBaseSeedId() {
        return BlueOceansItems.LEEK_SEEDS.get();
    }

    protected int getBonemealAgeIncrease(Level pLevel) {
        return super.getBonemealAgeIncrease(pLevel) / 2;
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    public int getMaxAge() {
        return 3;
    }

    static {
        SHAPE = ObjectArray.of(box(0, 0, 0, 16, 2, 16),
                box(0, 0, 0, 16, 3, 16),
                box(0, 0, 0, 16, 5, 16),
                box(0, 0, 0, 16, 7, 16));
    }
}
