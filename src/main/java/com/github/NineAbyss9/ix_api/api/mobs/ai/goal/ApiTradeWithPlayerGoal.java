
package com.github.NineAbyss9.ix_api.api.mobs.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.Merchant;

import java.util.EnumSet;

public class ApiTradeWithPlayerGoal extends Goal {
    protected final Mob mob;
    protected final Merchant merchant;
    public ApiTradeWithPlayerGoal(Merchant pMob) {
        this.mob = (Mob)pMob;
        this.merchant = pMob;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.mob.isAlive()) {
            return false;
        } else if (this.mob.isInWater()) {
            return false;
        } else if (!this.mob.onGround()) {
            return false;
        } else if (this.mob.hurtMarked) {
            return false;
        } else if (this.mob.getTarget() != null) {
            return false;
        } else {
            Player $$0 = this.merchant.getTradingPlayer();
            if ($$0 == null) {
                return false;
            } else return !(this.mob.distanceToSqr($$0) > 16.0);
        }
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    public void start() {
        this.mob.getNavigation().stop();
    }

    public void tick() {
        Player player = this.merchant.getTradingPlayer();
        if (player != null) {
            this.mob.getLookControl().setLookAt(player, 30, 30);
        }
    }

    public void stop() {
        this.merchant.setTradingPlayer(null);
    }
}
