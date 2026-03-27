
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.RedPlumCatalystEntity;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumHolder;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumMonster;
import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansGameRules;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.github.NineAbyss9.ix_api.api.annotation.ServerOnly;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.NineAbyss9.annotation.doc.Message;
import org.NineAbyss9.math.MathSupport;

public class RedPlumCatalyst
extends RedPlumBlock {
    public RedPlumCatalyst() {
        super(Properties.of().mapColor(DyeColor.RED).strength(2F, 30F)
                .randomTicks().sound(SoundType.SCULK).lightLevel(value -> 3));
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RedPlumCatalystEntity(pPos, pState);
    }

    @Nullable
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T pBlockEntity) {
        return pBlockEntity instanceof RedPlumCatalystEntity entity ? entity.getListener() : null;
    }

    protected void random25Action(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        for (int i = 0; i<5;i++) {
            spreadPlum(pLevel, pPos);
        }
    }

    public int getLevel() {
        return 2;
    }

    public int getExpDrop(BlockState state, LevelReader level, RandomSource radS, BlockPos pos, int fortuneLevel,
                          int silkTouchLevel) {
        return 4;
    }

    protected void spawnPlum(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (BoCommonConfig.SPAWN_NEO_PLUM.get() && pRandom.nextFloat() < 0.15F
        && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND,
                pLevel, pPos.above(), BlueOceansEntities.RED_PLUM_HUMAN.get())
        && pLevel.getEntitiesOfClass(RedPlumMonster.class, new AABB(pPos).inflate(10)).size() < 4) {
            PlumHolder.spawn(pLevel, pPos, 1);
        }
    }

    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getItemInHand(pHand).is(Items.EXPERIENCE_BOTTLE)) {
            if (!pLevel.isClientSide) {
                for (int i = 0;i < 5;i++) {
                    spreadPlum((ServerLevel)pLevel, pPos);
                }
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static void spreadPlum(ServerLevel pLevel, Vec3 pPos) {
        spreadPlum(pLevel, BlockPos.containing(pPos));
    }

    @ServerOnly
    public static void spreadPlum(ServerLevel serverLevel, @Message("Blocks do not below()") BlockPos center) {
        BlockPos pos = center.offset(Maths.randomInt(3), 0, Maths.randomInt(3));
        BlockState newBlockstate = getRandomGrowthState(serverLevel, pos);
        if (serverLevel.getGameRules().getBoolean(BlueOceansGameRules.PLUM_SPREAD) &&
                canPlaceGrowth(serverLevel, pos, newBlockstate.getBlock())) {
            serverLevel.setBlock(pos, newBlockstate, 3);
            serverLevel.playSound(null, center, newBlockstate.getSoundType().getPlaceSound(),
                    SoundSource.BLOCKS, 1.0F, 1.0F);
            BlockPos top = pos.above();
            spreadPlumTop(serverLevel.getBlockState(top), top, serverLevel);
        }
    }

    public static BlockState getRandomGrowthState(Level pLevel, BlockPos pPos) {
        BlockState blockstate;
        float rad = MathSupport.random.nextFloat();
        if (rad < 0.1F) {
            blockstate = BlueOceansBlocks.RED_PLUM_CATALYST.get().defaultBlockState();
        } else if (rad < 0.35F) {
            blockstate = BlueOceansBlocks.RED_PLUM_TRAP.get().defaultBlockState();
        } else if (rad < 0.5F) {
            blockstate = BlueOceansBlocks.SCLEROTIC_RED_PLUM_BLOCK.get().defaultBlockState();
        } else {
            blockstate = BlueOceansBlocks.RED_PLUM_BLOCK.get().defaultBlockState();
        }
        return blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && !pLevel.getFluidState(pPos).isEmpty()
                ? blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE) : blockstate;
    }

    private static boolean canPlaceGrowth(Level pLevel, BlockPos pPos, Block newBlock) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (checkOtherRules(blockstate) || checkBlock(blockstate.getBlock(), newBlock)) {
            int i = 0;
            for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4,
                    2, 4))) {
                BlockState blockstate1 = pLevel.getBlockState(blockpos);
                if (blockstate1.is(BlueOceansBlocks.RED_PLUM_CATALYST.get())) {
                    ++i;
                }
                if (i > 2) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkOtherRules(BlockState pState) {
        return pState.isAir() || pState.is(Blocks.WATER) && pState.getFluidState().is(Fluids.WATER);
    }

    private static boolean checkBlock(Block pBlock, Block newBlock) {
        if (pBlock instanceof PlumBlock plumBlock && newBlock instanceof PlumBlock iPlumBlock) {
            return plumBlock.getLevel() < iPlumBlock.getLevel();
        } else {
            return pBlock.defaultDestroyTime() < 9F;
        }
    }

    private static void spreadPlumTop(BlockState pState, BlockPos pPos, ServerLevel pLevel) {
        float chance = MathSupport.random.nextFloat();
        if (chance < 0.25F && pLevel.getBlockState(pPos.below()).is(BlueOceansBlocks.RED_PLUM_BLOCK.get())
            && !pLevel.getBlockState(pPos).is(BoTags.RED_PLUM_BLOCKS)) {
            RedPlumVein vein = (RedPlumVein)BlueOceansBlocks.RED_PLUM_VEIN.get();
            BlockState state = vein.getStateForPlacement(pState, pLevel, pPos, Direction.DOWN);
            if (state != null)
                pLevel.setBlock(pPos, state, 3);
        } else if (checkOtherRules(pState)) {
            if (chance < 0.5F)
                pLevel.setBlock(pPos, BlueOceansBlocks.RED_PLUM_GRASS.get().defaultBlockState(), 3);
            else if (chance < 0.75F)
                pLevel.setBlock(pPos, BlueOceansBlocks.PLUM_CELL_CLUSTER.get().defaultBlockState(), 3);
        }
    }
}
