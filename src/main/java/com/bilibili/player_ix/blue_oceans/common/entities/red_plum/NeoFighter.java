
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.player_ix.ix_api.api.mobs.ai.goal.ApiMeleeAttackGoal;
import com.github.player_ix.ix_api.util.Vec9;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

/**Sometimes refresh is used instead of NeoPlum.*/
public class NeoFighter
extends NeoPlum {
    public NeoFighter(EntityType<? extends NeoFighter> type, Level level) {
        super(type, level);
        this.xpReward = 2;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ApiMeleeAttackGoal(this, 0.8));
        super.registerGoals();
    }

    protected int nextConvertUpNeeds() {
        return 1;
    }

    public boolean shouldAttackOtherMobs() {
        return true;
    }

    protected boolean shouldLevelUp() {
        return this.getInfectLevel() >= this.nextConvertUpNeeds();
    }

    public void spawnBreedMob(LivingEntity pEntity) {
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        List<EntityType<? extends AbstractRedPlumMob>> list = RedPlumUtil.MAP.get(1);
        if (list != null) {
            return list.get(this.getRandomUtil().nextInt(RedPlumUtil.BASE_PLUM_RANDOM_POOL));
        } else {
            return null;
        }
    }

    public void doKillEntity(LivingEntity pEntity) {
        if (!this.level().isClientSide) {
            ServerLevel serverLevel = this.serverLevel();
            var entityType = this.getNextLevelConvert();
            AbstractRedPlumMob mob = null;
            if (entityType != null) {
                mob = entityType.create(serverLevel);
            }
            if (mob != null) {
                mob.moveTo(this.position());
                if (serverLevel.addFreshEntity(mob)) {
                    this.discard();
                }
            }
        }
    }

    @Nullable
    public static NeoFighter create(BlockPos pPos, Level pLevel) {
        return create(Vec9.of(pPos), pLevel);
    }

    @Nullable
    public static NeoFighter create(Vec3 pos, Level pLevel) {
        NeoFighter fighter = BlueOceansEntities.NEO_FIGHTER.get().create(pLevel);
        if (fighter != null) {
            fighter.moveTo(pos);
            pLevel.addFreshEntity(fighter);
        }
        return fighter;
    }
}
