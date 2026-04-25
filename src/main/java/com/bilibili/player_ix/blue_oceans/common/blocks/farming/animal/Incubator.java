
package com.bilibili.player_ix.blue_oceans.common.blocks.farming.animal;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class Incubator
extends Block
{
    public static final IntegerProperty AGE;
    public static final BooleanProperty STARTED;
    public static final IntegerProperty MOB;
    public Incubator(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0)
                .setValue(STARTED, false));
    }

    public Incubator()
    {
        this(Properties.of().noCollission().strength(5.0F, 20.0F)
                .mapColor(MapColor.METAL).lightLevel(i -> 5)
                .sound(SoundType.METAL).requiresCorrectToolForDrops());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(AGE, STARTED);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(AGE) > 0) {
            if (pLevel.getGameTime() % 40L == 0L) {
                pLevel.setBlock(pPos, pState.setValue(AGE, pState.getValue(AGE) - 1), 1);
            }
        } else {
            if (pState.getValue(STARTED)) {
                Chicken chicken = EntityType.CHICKEN.create(pLevel);
                if (chicken == null) return;
                chicken.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(pPos),
                        MobSpawnType.BREEDING, null, null);
                chicken.setBaby(true);
                chicken.moveTo(pPos, 0, 0);
                pLevel.addFreshEntity(chicken);
            }
        }
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return Shapes.empty();
    }

    static {
        AGE = BoBlockProperties.GROWTH_AGE;
        STARTED = BoBlockProperties.ACTIVATED;
        MOB = IntegerProperty.create("mob", 0, 6);
    }
}
