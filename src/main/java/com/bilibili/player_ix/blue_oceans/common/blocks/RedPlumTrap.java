
package com.bilibili.player_ix.blue_oceans.common.blocks;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RedPlumTrap
extends RedPlumBlock {
    public RedPlumTrap() {
        super();
    }

    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity instanceof LivingEntity livingEntity && !(livingEntity instanceof RedPlumMob)) {
            MobEffectInstance mobEffectInstance =
                    new MobEffectInstance(BlueOceansMobEffects.PLUM_INVADE.get(), 40, 1);
            if (livingEntity.canBeAffected(mobEffectInstance))
                livingEntity.addEffect(mobEffectInstance);
            else {
                if (livingEntity.isAlive()) {
                    livingEntity.hurt(pLevel.damageSources().dryOut(), 1F);
                }
            }
        }
    }

    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return entity instanceof AbstractRedPlumMob ? super.getFriction(state, level, pos, entity)
                : 0.95F;
    }

    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel,
                          int silkTouchLevel) {
        return 1;
    }

    protected void spawnPlum(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
    }
}
