
package com.bilibili.player_ix.blue_oceans.common.blocks.core;

import com.bilibili.player_ix.blue_oceans.compat.spore.SporeCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.array.ObjectArray;

import java.util.List;

public abstract class AbstractMushroom
extends CropBlock {
    protected static final ObjectArray<VoxelShape> MUSHROOM_SHAPE;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public AbstractMushroom(Properties pProperties) {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return MUSHROOM_SHAPE.get(this.getAge(pState));
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return false;
    }

    public int getMaxAge() {
        return 3;
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if (SporeCompat.isSporeLoaded() && pRandom.nextFloat() < 0.01F) {
            List<LivingEntity> entities =
                    pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos).inflate(2));
            pLevel.playSound(null, pPos, SporeCompat.getSporeSound("puff"), SoundSource.BLOCKS,
                    1.0F, 1.0F);
            if (!entities.isEmpty())
                entities.forEach(e->e.addEffect(new MobEffectInstance(
                        SporeCompat.getSporeEffect("spore:mycelium_ef"), 30, 0)));
        }
    }

    static {
        MUSHROOM_SHAPE = ObjectArray.withSize(3,
                box(1.0, 0.0, 1.0, 10.0, 10.0, 10.0),
                box(2.0, 0.0, 2.0, 11.0, 11.0, 11.0),
                box(4.0, 4.0, 4.0, 13.0, 13.0, 13.0));
    }
}
