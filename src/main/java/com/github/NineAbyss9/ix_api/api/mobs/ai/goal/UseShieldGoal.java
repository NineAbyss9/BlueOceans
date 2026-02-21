
package com.github.NineAbyss9.ix_api.api.mobs.ai.goal;

import com.github.NineAbyss9.ix_api.api.mobs.IShieldUser;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.NineAbyss9.math.MathSupport;
import org.NineAbyss9.util.IXUtil;
import org.NineAbyss9.util.IXUtilUser;

import java.util.EnumSet;

public class UseShieldGoal<E extends Mob & IShieldUser>
extends Goal implements IXUtilUser {
    private final E mob;
    public UseShieldGoal(E pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (this.mob.isShieldOnCooldown())
            return false;
        return this.mob.isAggressive() || this.mob.getTarget() != null;
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    public void start() {
        this.mob.setUsingShield(true);
    }

    public void tick() {
        this.mob.getMoveControl().strafe(-0.75F, MathSupport.random.nextBoolean() ?
                0.75F : -0.75F);
    }

    public void stop() {
        this.mob.setUsingShield(false);
    }

    protected <T> T convert() {
        return IXUtil.c.convert(this.mob);
    }
}
