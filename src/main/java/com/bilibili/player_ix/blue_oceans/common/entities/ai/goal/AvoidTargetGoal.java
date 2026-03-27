
package com.bilibili.player_ix.blue_oceans.common.entities.ai.goal;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;

public class AvoidTargetGoal extends AvoidEntityGoal<LivingEntity>
{
    public AvoidTargetGoal(PathfinderMob pMob, float range, double speed, double sprintSpeed)
    {
        super(pMob, LivingEntity.class, range, speed, sprintSpeed, living -> living != null &&
                EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(living));
    }

    public boolean canUse()
    {
        if (this.toAvoid instanceof Mob target) {
            if (target.getTarget() == this.mob)
                return super.canUse();
            return false;
        } else
            return super.canUse() && this.toAvoid instanceof Player;
    }
}
