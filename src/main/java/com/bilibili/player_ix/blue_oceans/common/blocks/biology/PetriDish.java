
package com.bilibili.player_ix.blue_oceans.common.blocks.biology;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.PetriDishEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

@SuppressWarnings("deprecation")
public class PetriDish
extends BaseEntityBlock {
    public PetriDish(Properties pProperties) {
        super(pProperties);
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player)
    {
        var stack = new ItemStack(this);
        level.getBlockEntity(pos).saveToItem(stack);
        return stack;
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
                                                                  BlockEntityType<T> pBlockEntityType)
    {
        return createTickerHelper(null, null, PetriDishEntity::tick);
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PetriDishEntity(pPos, pState);
    }
}
