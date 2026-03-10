
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoPlum;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumMonster;
import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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
        return entity instanceof AbstractRedPlumMob ? 0.5F : 0.95F;
    }

    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel,
                          int silkTouchLevel) {
        return 1;
    }

    protected void spawnPlum(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (BoCommonConfig.SPAWN_NEO_PLUM.get() && pRandom.nextFloat() < 0.05F
                && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND,
                pLevel, pPos.above(), BlueOceansEntities.NEO_PLUM.get())
                && pLevel.getEntitiesOfClass(RedPlumMonster.class, new AABB(pPos).inflate(16)).size() < 8) {
            AbstractRedPlumMob plum = NeoPlum.createRandom(pPos.above(), pLevel);
            if (plum != null) {
                NeoPlum.addParticleAroundPlum(plum);
            }
        }
    }

    protected void random25Action(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
    }
}
