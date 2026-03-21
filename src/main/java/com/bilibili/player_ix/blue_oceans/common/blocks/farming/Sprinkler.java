
package com.bilibili.player_ix.blue_oceans.common.blocks.farming;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.common.blocks.be.farming.SprinklerEntity;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Sprinkler
extends BaseEntityBlock {
    public static final BooleanProperty ACTIVATED;
    private static final VoxelShape SHAPE;
    public Sprinkler(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVATED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ACTIVATED);
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        pLevel.setBlock(pPos, pState.cycle(ACTIVATED), 3);
        pLevel.playSound(null, pPos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS);
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
                                                                  BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlueOceansBlockEntities.SPRINKLER.get(), SprinklerEntity::tick);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SprinklerEntity(pPos, pState);
    }

    static {
        ACTIVATED = BoBlockProperties.ACTIVATED;
        SHAPE = box(6, 0, 6, 10, 2, 10);
    }
}
