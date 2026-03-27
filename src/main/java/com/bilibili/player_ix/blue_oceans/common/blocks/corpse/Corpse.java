
package com.bilibili.player_ix.blue_oceans.common.blocks.corpse;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.CorpseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**Corpse class for all corpses.*/
@SuppressWarnings("deprecation")
public class Corpse
extends BaseEntityBlock
{
    protected static final VoxelShape BASE_SHAPE;
    public Corpse(Properties pProperties)
    {
        super(pProperties);
    }

    public Corpse()
    {
        this(Properties.of().sound(SoundType.MUD).strength(1.0f).noCollission()
                .mapColor(DyeColor.RED));
    }

    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState)
    {
        if (pLevel instanceof Level level && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) &&
                !pLevel.isClientSide() && pLevel.getBlockEntity(pPos) instanceof CorpseEntity entity)
            entity.drop();
        super.destroy(pLevel, pPos, pState);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new CorpseEntity(pPos, pState);
    }

    static {
        BASE_SHAPE = box(4d, 0d, 4d, 12d, 2d, 12d);
    }
}
