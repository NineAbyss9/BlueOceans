
package com.bilibili.player_ix.blue_oceans.common.blocks.nature;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

@SuppressWarnings("deprecation")
public class Bush
extends BushBlock {
    public Bush(Properties pProperties) {
        super(pProperties);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isDay() && pRandom.nextFloat() < 0.1F) {
            List<LivingEntity> entities = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos).inflate(1));
            if (!entities.isEmpty()) {
                entities.forEach(entity -> entity.addEffect(
                        new MobEffectInstance(BlueOceansMobEffects.COMFORTABLE.get(), 180, 0)));
            }
        }
    }
}
