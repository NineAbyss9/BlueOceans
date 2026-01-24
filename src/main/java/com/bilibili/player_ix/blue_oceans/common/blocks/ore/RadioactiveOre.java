
package com.bilibili.player_ix.blue_oceans.common.blocks.ore;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

@SuppressWarnings("deprecation")
public abstract class RadioactiveOre
extends AbstractElementOre
implements Radioactive {
    public RadioactiveOre(float pDestroyTime, float pExplosionResistance, SoundType pSoundType, DyeColor pColor, Element pElement) {
        super(pDestroyTime, pExplosionResistance, pSoundType, pColor, pElement);
    }

    public RadioactiveOre(Properties pProperties, IntProvider xpRange, Element pElement) {
        super(pProperties, xpRange, pElement);
    }

    public RadioactiveOre(Properties pProperties, Element pElement) {
        super(pProperties, pElement);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(6) == 0) {
            List<LivingEntity> entities = pLevel.getEntitiesOfClass(LivingEntity.class,
                    new AABB(pPos).inflate(4));
            if (!entities.isEmpty()) {
                entities.forEach(entity -> entity.heal(9));
            }
        }
    }
}
