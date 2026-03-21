
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IChemical;
import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.math.AbyssMath;

@SuppressWarnings("deprecation")
public class AlcoholLamp
extends Block
implements IChemical
{
    public static final BooleanProperty COVERED = BoBlockProperties.COVERED;
    public static final IntegerProperty CAPACITY = BoBlockProperties.CAPACITY;
    public static final BooleanProperty BURNING = BoBlockProperties.BURNING;
    private static final VoxelShape SHAPE = box(3.0, 0.0, 3.0, 13.0, 10.0, 13.0);
    public AlcoholLamp(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(COVERED, false)
                .setValue(CAPACITY, 8).setValue(BURNING, false));
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(BURNING)) {
            pLevel.addParticle(ParticleTypes.FLAME, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5,
                    AbyssMath.trueOrFalse(pRandom.nextDouble() * 0.05),
                    AbyssMath.trueOrFalse(pRandom.nextDouble() * 0.05),
                    AbyssMath.trueOrFalse(pRandom.nextDouble() * 0.05));
            if (pRandom.nextFloat() <= 0.05F) {
                pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.FIRE_AMBIENT,
                        SoundSource.BLOCKS, 1.0F, 1.0F - pRandom.nextFloat() * 0.25F, false);
            }
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (!pState.getValue(BURNING) && pState.getValue(CAPACITY) > 0 && !pState.getValue(COVERED) &&
                itemStack.is(Items.FLINT_AND_STEEL)) {
            pState = pState.setValue(BURNING, true);
            pLevel.setBlock(pPos, pState, 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else if (pState.getValue(BURNING)) {
            pLevel.setBlock(pPos, pState.cycle(BURNING), 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(BURNING)) {
            if (pState.getValue(CAPACITY) > 0) {
                if (pLevel.getGameTime() % 2400L == 0L)
                    pLevel.setBlock(pPos, pState.setValue(CAPACITY, pState.getValue(CAPACITY) - 1), 3);
            } else
                pLevel.setBlock(pPos, pState.setValue(BURNING, false), 3);
        }
    }

    /*public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (isLit(pState)) {
            if (!pEntity.fireImmune()) {
                pEntity.setRemainingFireTicks(pEntity.getRemainingFireTicks() + 1);
                if (pEntity.getRemainingFireTicks() == 0) {
                    pEntity.setSecondsOnFire(6);
                }
            }
            pEntity.hurt(pLevel.damageSources().onFire(), FIRE_DAMAGE);
        }
    }*/

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pState.getValue(BURNING)) {
            pEntity.hurt(pLevel.damageSources().inFire(), BoCommonConfig.ALCOHOL_LAMP_DAMAGE.get().floatValue());
        }
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(COVERED, CAPACITY, BURNING);
    }
}
