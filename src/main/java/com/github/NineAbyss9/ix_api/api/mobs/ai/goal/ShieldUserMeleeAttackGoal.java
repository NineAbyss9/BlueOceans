
package com.github.NineAbyss9.ix_api.api.mobs.ai.goal;

import com.github.NineAbyss9.ix_api.api.mobs.IShieldUser;
import net.minecraft.world.entity.PathfinderMob;

public class ShieldUserMeleeAttackGoal<E extends PathfinderMob & IShieldUser>
extends ApiMeleeAttackGoal {
    public ShieldUserMeleeAttackGoal(E finder, double speed, double attackRange) {
        super(finder, speed, attackRange);
    }

    public ShieldUserMeleeAttackGoal(E finder, double speed) {
        super(finder, speed);
    }

    public boolean canUse() {
        if (((IShieldUser)this.convert()).isUsingShield())
            return false;
        return super.canUse();
    }

    public boolean canContinueToUse() {
        if (((IShieldUser)this.convert()).isUsingShield())
            return false;
        return super.canContinueToUse();
    }
}
