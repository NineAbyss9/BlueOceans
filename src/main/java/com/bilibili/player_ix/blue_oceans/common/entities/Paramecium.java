
package com.bilibili.player_ix.blue_oceans.common.entities;

import com.bilibili.player_ix.blue_oceans.api.mob.IBONeutralMob;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.BOMeleeAttackGoal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

public class Paramecium extends AbstractBlueOceansMob
implements IBONeutralMob {
    private final WaterBoundPathNavigation waterPath;
    private final GroundPathNavigation path;
    public Paramecium(EntityType<? extends Paramecium> type, Level level) {
        super(type, level);
        this.moveControl = new ParameciumMoveControl(this);
        this.path = new GroundPathNavigation(this, this.level());
        this.waterPath = new WaterBoundPathNavigation(this, this.level());
        this.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
        this.refreshDimensions();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new AttackGoal(this));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this,
                2, 40));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Paramecium.class));
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01f, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null)
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
        } else
            super.travel(pTravelVector);
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean isMutated() {
        return true;
    }

    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if (this.isEffectiveAi() && this.isInWater()) {
                this.navigation = this.waterPath;
                this.setSwimming(true);
            } else {
                this.navigation = this.path;
                this.setSwimming(false);
            }
        }
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.getHurtSound(this.damageSources().cactus());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(ForgeMod.SWIM_SPEED.get(), 1).add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 3);
    }

    private class AttackGoal extends BOMeleeAttackGoal {
        public AttackGoal(PathfinderMob pMob) {
            super(pMob, 1, false);
        }

        public boolean canUse() {
            return Paramecium.this.isMutated() && super.canUse();
        }
    }

    public static class ParameciumMoveControl extends MoveControl {
        public ParameciumMoveControl(Mob pMob) {
            super(pMob);
        }

        public void tick() {
            if (this.mob.isInWater()) {
                this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0, 0.0025, 0));
                if (this.hasWanted() && !this.mob.getNavigation().isDone()) {
                    double dx = this.wantedX - this.mob.getX();
                    double dy = this.wantedY - this.mob.getY();
                    double dz = this.wantedZ - this.mob.getZ();
                    float f = (float) (Mth.atan2(dz, dx) * (180 / Math.PI)) - 90;
                    float f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 10));
                    this.mob.yBodyRot = this.mob.getYRot();
                    this.mob.yHeadRot = this.mob.getYRot();
                    if (this.mob.isInWater()) {
                        this.mob.setSpeed((float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        float f2 = -(float) (Mth.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85, 85);
                        this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, 5));
                        float f3 = Mth.cos(this.mob.getXRot() * (float) (Math.PI / 180.0));
                        this.mob.setZza(f3 * f1);
                        this.mob.setYya((float) (f1 * dy));
                    } else
                        this.mob.setSpeed(f1 * 0.5F);
                } else {
                    this.mob.setSpeed(0.0f);
                    this.mob.setYya(0.0f);
                    this.mob.setZza(0.0f);
                }
                this.mob.refreshDimensions();
            } else
                super.tick();
        }
    }
}
