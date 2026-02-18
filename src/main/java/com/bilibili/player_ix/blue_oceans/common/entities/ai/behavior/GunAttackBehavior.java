
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior;

import com.bilibili.player_ix.blue_oceans.common.item.gun.AbstractGun;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.NineAbyss9.math.number.RangedInt;

import java.util.EnumSet;

public class GunAttackBehavior extends Behavior {
    public static final RangedInt PATHFINDING_DELAY_RANGE =
            RangedInt.of(20, 40);
    private final Mob mob;
    private final double speedModifier;
    private final float attackRadiusSqr;
    private int seeTime;
    private int attackDelay;
    private int updatePathDelay;
    public GunAttackBehavior(Mob pSolider, double pSpeed, float pAttackRange) {
        this.mob = pSolider;
        this.speedModifier = pSpeed;
        this.attackRadiusSqr = pAttackRange * pAttackRange;
        this.setFlags(EnumSet.of(BehaviorFlag.MOVE, BehaviorFlag.LOOK));
    }

    protected boolean isHoldingGun() {
        return this.mob.isHolding(itemStack -> itemStack.getItem() instanceof AbstractGun);
    }

    protected boolean isValidTarget() {
        return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
    }

    public boolean canUse() {
        return this.isValidTarget() && this.isHoldingGun();
    }

    public void start() {
        this.mob.setAggressive(true);
        this.mob.startUsingItem(InteractionHand.MAIN_HAND);
    }

    public boolean canContinueToUse() {
        return this.isValidTarget() && (this.canUse() || !this.mob.getNavigation().isDone())
                && this.isHoldingGun();
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
                this.seeTime = 0;
            }
            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }
            double d0 = this.mob.distanceToSqr(livingentity);
            boolean flag2 = (d0 > this.attackRadiusSqr || this.seeTime < 5);
            this.moveOrStrafe(livingentity, flag2);
            this.mob.getLookControl().setLookAt(livingentity, 30F, 30F);
            AbstractGun gun = (AbstractGun)this.mob.getUseItem().getItem();
            if (gun.isNoCooling()) {
                if (this.mob.tickCount % 3 == 0) {
                    gun.fire(this.mob.getUseItem(), this.mob.level(), this.mob);
                }
            } else {
                if (this.attackDelay <= 0) {
                    gun.fire(this.mob.getUseItem(), this.mob.level(), this.mob);
                    this.attackDelay = gun.getUseTime();
                } else {
                    --this.attackDelay;
                }
            }
        }
    }

    public void moveOrStrafe(LivingEntity target, boolean flag) {
        if (flag) {
            --this.updatePathDelay;
            if (this.updatePathDelay <= 0) {
                this.mob.getNavigation().moveTo(target, this.speedModifier);
                this.updatePathDelay = PATHFINDING_DELAY_RANGE.sample();
            }
        } else {
            this.updatePathDelay = 0;
            if (!this.mob.getNavigation().isDone()) {
                mob.getNavigation().stop();
            }
            mob.getMoveControl().strafe(-0.75F, this.getStrafe());
            mob.setYRot(Mth.rotateIfNecessary(mob.getYRot(), mob.yHeadRot, 0.0F));
        }
    }

    protected float getStrafe() {
        if (this.likeArcher()) {
            return this.mob.getRandom().nextBoolean() ? -0.75F : 0.75F;
        } else {
            return 0.0F;
        }
    }

    protected boolean likeArcher() {
        return false;
    }

    public void stop() {
        this.mob.setAggressive(false);
        this.mob.setTarget(null);
        this.seeTime = 0;
        if (this.mob.isUsingItem()) {
            this.mob.stopUsingItem();
        }
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
