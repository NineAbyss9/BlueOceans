
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import com.bilibili.player_ix.blue_oceans.common.chemistry.IChemical;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.nine_abyss.math.AbyssMath;

@SuppressWarnings("deprecation")
public class AlcoholLamp
extends Block
implements IChemical {
    public static final BooleanProperty COVERED = BooleanProperty.create("covered");
    public static final IntegerProperty CAPACITY = IntegerProperty.create("capacity",
            0, 8);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static float FIRE_DAMAGE = 4.0F;
    private static final VoxelShape SHAPE = box(3.0, 0.0, 3.0, 13.0, 10.0, 13.0);
    public AlcoholLamp(Properties pProperties) {
        super(pProperties);
        this.stateDefinition.any().setValue(COVERED, Boolean.FALSE).setValue(CAPACITY, 8)
                .setValue(LIT, Boolean.FALSE);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (isLit(pState)) {
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
        if (!isLit(pState) && getCapacity(pState) > 0 && !isCovered(pState) &&
                itemStack.is(Items.FLINT_AND_STEEL)) {
            setLit(pState, true);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
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
        if (isLit(pState)) {
            pEntity.hurt(pLevel.damageSources().inFire(), FIRE_DAMAGE);
        }
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();
        setCovered(state, false);
        setLit(state, false);
        setCapacity(state, 8);
        return state;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(COVERED, CAPACITY, LIT);
    }

    public static void setFireDamage(float f) {
        FIRE_DAMAGE = f;
    }

    public static boolean isLit(BlockState state) {
        return state.getValue(LIT);
    }

    public static void setLit(BlockState state, boolean lit) {
        state.setValue(LIT, lit);
    }

    public static int getCapacity(BlockState state) {
        return state.getValue(CAPACITY);
    }

    public static void setCapacity(BlockState state, int c) {
        state.setValue(CAPACITY, AbyssMath.clamp(c, 0, 8));
    }

    public static boolean isCovered(BlockState state) {
        return state.getValue(COVERED);
    }

    public static void setCovered(BlockState state, boolean covered) {
        state.setValue(COVERED, covered);
    }
}
