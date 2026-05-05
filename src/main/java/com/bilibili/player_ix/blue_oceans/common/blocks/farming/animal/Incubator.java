
package com.bilibili.player_ix.blue_oceans.common.blocks.farming.animal;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansHooks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.data.ModBlockStateProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Incubator
extends Block
implements ModBlockStateProvider.MainAndOtherSide
{
    public static final IntegerProperty AGE;
    public static final BooleanProperty STARTED;
    /**0->chicken
     * 1->duck*/
    public static final IntegerProperty MOB;
    public static final DirectionProperty FACING;
    public Incubator(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0)
                .setValue(STARTED, false).setValue(MOB, 0).setValue(FACING,
                        Direction.NORTH));
    }

    public Incubator()
    {
        this(Properties.of().noCollission().strength(3.0F, 10.0F)
                .mapColor(MapColor.METAL).lightLevel(i -> 5)
                .sound(SoundType.METAL).requiresCorrectToolForDrops());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(AGE, STARTED, MOB, FACING);
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player)
    {
        var stack = BlueOceansItems.INCUBATOR.get().getDefaultInstance();
        stack.getOrCreateTag().putInt(AGE.getName(), state.getValue(AGE));
        stack.getTag().putBoolean(STARTED.getName(), state.getValue(STARTED));
        stack.getTag().putInt(MOB.getName(), state.getValue(MOB));
        return stack;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(AGE) > 0) {
            if (pLevel.getGameTime() % 40L == 0L) {
                pLevel.setBlock(pPos, pState.setValue(AGE, pState.getValue(AGE) - 1), 1);
            }
        } else {
            if (pState.getValue(STARTED)) {
                Mob mob = switch (pState.getValue(MOB)) {
                    case 1 -> BlueOceansEntities.DUCK.get().create(pLevel);
                    case 2 -> BlueOceansEntities.NEO_PLUM.get().create(pLevel);
                    default -> EntityType.CHICKEN.create(pLevel);
                };
                mob.moveTo(pPos, 0, 0);
                if (mob == null || !BlueOceansHooks.onFinalizeSpawn(mob, pLevel, pLevel.getCurrentDifficultyAt(pPos),
                        MobSpawnType.BREEDING)) return;
                mob.setBaby(true);
                pLevel.addFreshEntity(mob);
                pLevel.setBlock(pPos, pState.cycle(STARTED), 1);
            }
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit)
    {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        boolean flag = false;
        if (pState.getValue(STARTED)) {
            if (!pPlayer.isCrouching()) {
                if (pLevel.isClientSide) {
                    Minecraft.getInstance().gui.setOverlayMessage(Component.translatable("info.blue_oceans.incubator.age",
                            pState.getValue(AGE)), false);
                }
            } else {
                pPlayer.addItem(switch (pState.getValue(MOB)) {
                    case 2 -> BlueOceansItems.RED_PLUM.get().getDefaultInstance();
                    case 1 -> BlueOceansItems.DUCK_EGG.get().getDefaultInstance();
                    default -> Items.EGG.getDefaultInstance();
                });
                pLevel.setBlock(pPos, pState.cycle(STARTED).setValue(AGE, 0).setValue(MOB, 0), 2);
            }
            flag = true;
        }
        if (stack.is(Items.EGG)) {
            pLevel.setBlock(pPos, pState.setValue(MOB, 0), 1);
            flag = true;
        } else if (stack.is(BlueOceansItems.DUCK_EGG.get())) {
            pLevel.setBlock(pPos, pState.setValue(MOB, 1), 1);
            flag = true;
        }
        return flag ? InteractionResult.sidedSuccess(pLevel.isClientSide) : super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        if (!pContext.replacingClickedOnBlock()) {
            BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(
                    pContext.getClickedFace().getOpposite()));
            if (blockstate.is(this) && blockstate.getValue(FACING) == pContext.getClickedFace()) {
                return null;
            }
        }
        BlockState blockstate1 = this.defaultBlockState();
        for (Direction direction : pContext.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockstate1 = blockstate1.setValue(FACING, direction.getOpposite());
                return blockstate1;
            }
        }
        return null;
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    static {
        AGE = BoBlockProperties.GROWTH_AGE;
        STARTED = BoBlockProperties.ACTIVATED;
        MOB = IntegerProperty.create("mob", 0, 6);
        FACING = HorizontalDirectionalBlock.FACING;
    }
}
