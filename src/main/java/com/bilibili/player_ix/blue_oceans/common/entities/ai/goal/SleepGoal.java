
package com.bilibili.player_ix.blue_oceans.common.entities.ai.goal;

import com.bilibili.player_ix.blue_oceans.api.mob.ISleepMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.NineAbyss9.util.IXUtil;
import org.NineAbyss9.util.IXUtilUser;

import java.util.EnumSet;

public class SleepGoal<E extends Mob & ISleepMob>
extends Goal
implements IXUtilUser {
    private final E mob;
    public SleepGoal(E pEntity) {
        this.mob = pEntity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }

    public boolean canUse() {
        if (!this.mob.walkAnimation.isMoving())
            return this.mob.canSleep() || this.mob.isSleeping();
        return false;
    }

    public boolean canContinueToUse() {
        return this.mob.canSleep();
    }

    public void start() {
        this.mob.setJumping(false);
        this.mob.setSleeping(true);
        this.mob.getNavigation().stop();
        this.mob.getMoveControl().setWantedPosition(mob.getX(), mob.getY(), mob.getZ(), 0);
    }

    public void stop() {
        this.mob.setSleeping(false);
    }

    protected <T> T convert() {
        return IXUtil.c.convert(this.mob);
    }
}
