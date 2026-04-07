
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.NineAbyss9.math.MathSupport;
import org.NineAbyss9.util.Action;

public class PlumInvade extends MobEffect {
    public PlumInvade() {
        super(MobEffectCategory.BENEFICIAL, -12189696);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof RedPlumMob)) {
            Level entityLevel = pLivingEntity.level();
            if (pLivingEntity.isAlive()) {
                if (pLivingEntity.hurt(pLivingEntity.damageSources().dryOut(), 1 + pAmplifier)) {
                    if (MathSupport.random.nextFloat() < 0.2F) {
                        ServerLevel level = (ServerLevel)entityLevel;
                        BlockPos pos = pLivingEntity.blockPosition();
                        BlockState state = level.getBlockState(pos);
                        if (!state.is(BoTags.RED_PLUM_BLOCKS)) {
                            MultifaceBlock block = (MultifaceBlock)BlueOceansBlocks.BUDDING_NEO_PLUM.get();
                            BlockState newState = block.getStateForPlacement(state, level, pos, Direction.DOWN);
                            if (newState != null)
                                level.setBlockAndUpdate(pos, newState);
                        }
                    }
                }
            } else if (!entityLevel.isClientSide && pLivingEntity.deathTime == 1
                && !pLivingEntity.hasEffect(BlueOceansMobEffects.PLUM_INFECTION.get())) {
                AbstractRedPlumMob mob = entityLevel.getNearestEntity(AbstractRedPlumMob.class,
                        TargetingConditions.forNonCombat(), null, pLivingEntity.getX(),
                        pLivingEntity.getY(), pLivingEntity.getZ(),
                        pLivingEntity.getBoundingBox().inflate(6));
                if (mob != null) {
                    mob.setKillsPlus();
                    mob.checkAndPlusInfectLevel(pLivingEntity);
                }
                if (pLivingEntity.removeEffect(this)) {
                    if (RedPlumUtil.likeHuman(pLivingEntity)) {
                        RedPlumUtil.spawnRedPlumHuman(entityLevel, pLivingEntity);
                    } else if (RedPlumUtil.likeVillager(pLivingEntity)) {
                        RedPlumUtil.spawnRedPlumVillager(entityLevel, pLivingEntity);
                    /*} else {
                        var plum = NeoPlum.createRandom(pLivingEntity.position(), entityLevel);
                        if (plum != null) {
                            NeoPlum.addParticleAroundPlum(plum);
                        }*/
                    }
                    Action.emptyFalse(() -> entityLevel.setBlockAndUpdate(pLivingEntity.blockPosition(),
                                    BlueOceansBlocks.PLUM_TISSUE.get().defaultBlockState()))
                            .run(!entityLevel.getBlockState(pLivingEntity.blockPosition()).is(BoTags.RED_PLUM_BLOCKS));
                }
            }
        } else {
            if (pLivingEntity.tickCount % 40 == 0)
                pLivingEntity.heal(1.0F);
        }
    }

    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
