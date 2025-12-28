
package com.bilibili.player_ix.blue_oceans.api.ai.goal;

import com.github.player_ix.ix_api.api.mobs.ApiVillager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class BOAttackTargetGoal
extends NearestAttackableTargetGoal<LivingEntity> {
    public BOAttackTargetGoal(Mob pMob, boolean pMuseSee, @Nullable Predicate<LivingEntity> predicate) {
        super(pMob, LivingEntity.class, 10, pMuseSee, false, predicate);
    }

    public BOAttackTargetGoal(Mob pMob, boolean pMuseSee) {
        this(pMob, pMuseSee, entity -> entity instanceof ApiVillager || entity instanceof AbstractVillager
                || entity instanceof Player || entity instanceof AbstractGolem);
    }
}
