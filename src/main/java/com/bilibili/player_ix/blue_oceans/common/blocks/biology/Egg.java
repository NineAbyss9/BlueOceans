
package com.bilibili.player_ix.blue_oceans.common.blocks.biology;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@SuppressWarnings("deprecation")
public class Egg
extends Block
{
    protected static final IntegerProperty AGE;
    private final int maxAge;
    private final EntityType<? extends Mob> entityType;
    public Egg(Properties pProperties, int maxAgeIn, EntityType<? extends Mob> pEntityType)
    {
        super(pProperties);
        this.maxAge = maxAgeIn;
        this.entityType = pEntityType;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, maxAgeIn));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(AGE);
    }

    public int getMaxAge()
    {
        return maxAge;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(AGE) > 0) {
            if (pLevel.getGameTime() % 200L == 0L) {
                pLevel.setBlock(pPos, pState.setValue(AGE, pState.getValue(AGE) + 1), 1);
            }
        } else {
            var entity = this.entityType.create(pLevel);
            if (entity != null) {
                entity.moveTo(pPos, 0, 0);
                if (BlueOceansHooks.onFinalizeSpawn(entity, pLevel, pPos.getX(), pPos.getY(),
                        pPos.getZ(), pLevel.getCurrentDifficultyAt(pPos), MobSpawnType.BREEDING)) {
                    if (!pLevel.addFreshEntity(entity)) {
                        entity.discard();
                    }
                }
            }
            pLevel.destroyBlock(pPos, false);
        }
    }

    static {
        AGE = BoBlockProperties.GROWTH_AGE;
    }
}
