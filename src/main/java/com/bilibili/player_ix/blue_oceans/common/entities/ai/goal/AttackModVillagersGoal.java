
package com.bilibili.player_ix.blue_oceans.common.entities.ai.goal;

import com.bilibili.player_ix.blue_oceans.common.entities.villagers.BaseVillager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class AttackModVillagersGoal
extends NearestAttackableTargetGoal<LivingEntity> {
    public AttackModVillagersGoal(Mob pMob, boolean pMustSee) {
        super(pMob, LivingEntity.class, pMustSee, livingEntity -> {
            if (livingEntity instanceof BaseVillager villager) {
                return !villager.isAgent();
            }
            return false;
        });
    }

    /*public AttackModVillagersGoal(Mob pMob, boolean pMustSee, Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, BaseVillager.class, pMustSee, pTargetPredicate);
    }

    public AttackModVillagersGoal(Mob pMob, boolean pMustSee, boolean pMustReach) {
        super(pMob, BaseVillager.class, pMustSee, pMustReach);
    }

    public AttackModVillagersGoal(Mob pMob, int pRandomInterval, boolean pMustSee, boolean pMustReach,
                                  @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, BaseVillager.class, pRandomInterval, pMustSee, pMustReach, pTargetPredicate);
    }*/
}
