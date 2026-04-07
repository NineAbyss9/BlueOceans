
package com.bilibili.player_ix.blue_oceans.common.entities.animal.flying;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.Mouse;
import com.bilibili.player_ix.blue_oceans.util.MobUtil;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.NineAbyss9.util.Option;

import java.util.List;

/** Raptor: soars, then stoops on small ground prey and fish near the surface. */
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
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.35D));
        this.goalSelector.addGoal(2, new EagleAttackGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 0.55D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true,
                Eagle::isNaturalPrey));
    }

    private static boolean isNaturalPrey(LivingEntity e) {
        if (!e.isAlive()) {
            return false;
        }
        return e instanceof Rabbit || e instanceof Chicken || e instanceof AbstractFish || e instanceof Mouse;
    }

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, pLevel);
        pathNavigation.setCanFloat(true);
        pathNavigation.setCanPassDoors(false);
        pathNavigation.setCanOpenDoors(false);
        return pathNavigation;
    }

    protected void onFlap() {
        this.playAmbientSound();
    }

    public boolean shouldFlyUp() {
        return this.getTarget() != null && this.getTarget().getY() >= this.getY() + 10.0D;
    }

    public void flyUp() {
        this.moveControl.setWantedPosition(this.getX() + Maths.randomInteger(3),
                this.getY() + 12.0D, this.getZ() + Maths.randomInteger(3), 1.2D);
    }

    public void flyDown() {
        this.moveControl.setWantedPosition(this.getX(), MobUtil.ground(this), this.getZ(), 0.8D);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(ambient, fly, attack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.FLYING_SPEED, 0.55D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    public static class EagleAttackGoal extends Goal {
        private final Eagle eagle;
        private int flyTick;
        private int attackCooldown;

        public EagleAttackGoal(Eagle finder)
        {
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

        public boolean checkRange(LivingEntity pTarget) {
            return this.eagle.closerThan(pTarget, 2);
        }

        public void attack(LivingEntity pTarget) {
            this.eagle.doHurtTarget(pTarget);
            this.attackCooldown = 30;
        }

        public boolean canUse() {
            LivingEntity target = this.eagle.getTarget();
            return target != null && target.isAlive();
        }

        public boolean canContinueToUse() {
            return this.eagle.getTarget() != null;
        }
    }
}
