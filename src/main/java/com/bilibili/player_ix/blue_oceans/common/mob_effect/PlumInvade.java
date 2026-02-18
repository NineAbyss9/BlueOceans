
package com.bilibili.player_ix.blue_oceans.common.mob_effect;

import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoPlum;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.NineAbyss9.math.MathSupport;

public class PlumInvade extends MobEffect {
    public PlumInvade() {
        super(MobEffectCategory.BENEFICIAL, -12189696);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof RedPlumMob)) {
            if (pLivingEntity.isAlive()) {
                if (pLivingEntity.hurt(pLivingEntity.damageSources().dryOut(), 1 + pAmplifier)) {
                    if (MathSupport.random.nextFloat() < 0.2F) {
                        ServerLevel level = (ServerLevel)pLivingEntity.level();
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
            } else if (!pLivingEntity.level().isClientSide && pLivingEntity.deathTime == 1) {
                AbstractRedPlumMob mob = pLivingEntity.level().getNearestEntity(AbstractRedPlumMob.class,
                        TargetingConditions.forNonCombat(), null, pLivingEntity.getX(),
                        pLivingEntity.getY(), pLivingEntity.getZ(),
                        pLivingEntity.getBoundingBox().inflate(6));
                if (mob != null) {
                    mob.setKillsPlus();
                    mob.checkAndPlusInfectLevel(pLivingEntity);
                }
                if (pLivingEntity.removeEffect(this)) {
                    if (pLivingEntity instanceof Player || pLivingEntity instanceof Zombie) {
                        RedPlumUtil.spawnRedPlumHuman(pLivingEntity.level(), pLivingEntity);
                    } else if (pLivingEntity instanceof AbstractVillager) {
                        RedPlumUtil.spawnRedPlumVillager(pLivingEntity.level(), (AbstractVillager)pLivingEntity);
                    } else {
                        var plum = NeoPlum.createRandom(pLivingEntity.position(), pLivingEntity.level());
                        if (plum != null) {
                            NeoPlum.addParticleAroundPlum(plum);
                        }
                    }
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
