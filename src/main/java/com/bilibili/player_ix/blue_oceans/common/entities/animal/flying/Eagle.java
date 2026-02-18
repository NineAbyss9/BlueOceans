
package com.bilibili.player_ix.blue_oceans.common.entities.animal.flying;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.util.MobUtil;
import com.github.player_ix.ix_api.util.Maths;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import org.NineAbyss9.util.Option;

import java.util.List;

public class Eagle
extends AbstractFlyingAnimal
implements IAnimatedMob {
    public AnimationState ambient = new AnimationState();
    public AnimationState fly = new AnimationState();
    public AnimationState attack = new AnimationState();
    public Eagle(EntityType<? extends Eagle> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EagleAttackGoal(this));
    }

    public void aiStep() {
        super.aiStep();
    }

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, pLevel);
        pathNavigation.setCanFloat(true);
        pathNavigation.setCanPassDoors(false);
        pathNavigation.setCanOpenDoors(false);
        return pathNavigation;
    }

    protected void onFlap() {
        super.onFlap();
    }

    private boolean shouldFlyUp() {
        return this.getTarget() != null && this.getTarget().getY() >= this.getY() + 10.0D;
    }

    private void flyUp() {
        this.moveControl.setWantedPosition(this.getX() + Maths.randomInteger(3),
                this.getY() + 12.0D, this.getZ() + Maths.randomInteger(3), 1.2D);
    }

    private void flyDown() {
        this.moveControl.setWantedPosition(this.getX(), MobUtil.ground(this), this.getZ(), 0.8D);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(ambient, fly, attack);
    }

    private static class EagleAttackGoal extends Goal {
        private final Eagle eagle;
        private int flyTick;
        private int attackCooldown;
        public EagleAttackGoal(Eagle finder) {
            this.eagle = finder;
        }

        public void start() {
            this.eagle.setAggressive(true);
            if (this.eagle.shouldFlyUp()) {
                this.eagle.flyUp();
                this.flyTick = 0;
            }
            this.attackCooldown = 0;
        }

        public void tick() {
            if (this.attackCooldown > 0) {
                --this.attackCooldown;
            }
            if (this.eagle.shouldFlyUp() && this.flyTick < 41) {
                ++this.flyTick;
            }
            LivingEntity target = this.eagle.getTarget();
            Option.ofNullable(target).ifPresent(living -> {
                if (this.flyTick == 40) {
                    this.eagle.moveControl.setWantedPosition(living.getX(), living.getY(), living.getZ(),
                            1.5D);
                }
                if (this.checkRange(living)) {
                    this.attack(living);
                }
            });
        }

        public void stop() {
            this.eagle.setAggressive(false);
            this.flyTick = 0;
            this.eagle.flyDown();
        }

        private boolean checkRange(LivingEntity pTarget) {
            return this.eagle.closerThan(pTarget, 2);
        }

        private void attack(LivingEntity pTarget) {
            this.eagle.doHurtTarget(pTarget);
            this.attackCooldown = 30;
        }

        public boolean canUse() {
            LivingEntity target = this.eagle.getTarget();
            return target != null;
        }

        public boolean canContinueToUse() {
            return this.eagle.getTarget() != null;
        }
    }
}
