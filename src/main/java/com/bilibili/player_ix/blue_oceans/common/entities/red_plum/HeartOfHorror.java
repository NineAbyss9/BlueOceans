
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.Venom;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager.Dictator;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager.SpellcasterRedPlumIllager;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.EnumSet;

public class HeartOfHorror extends SpellcasterRedPlumIllager implements RangedAttackMob {
    private int ShootTicks;
    public HeartOfHorror(EntityType<? extends AbstractRedPlumMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.xpReward = XP_REWARD_LARGE;
    }

    public int getShootTicks() {
        return this.ShootTicks;
    }

    public void setShootTicks(int shootTicks) {
        this.ShootTicks = shootTicks;
    }

    public boolean isShooting() {
        return this.getShootTicks() > 0;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() == this) {
            return false;
        }
        if (pSource.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    public void handleEntityEvent(byte p_34138_) {
        if (p_34138_ == 15) {
            for(int $$1 = 0; $$1 < this.random.nextInt(10) + 10; ++$$1) {
                this.level().addParticle(ParticleTypes.WITCH, this.getX() + this.random.nextGaussian() * 1.3,
                        this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian() * 1.3, this.getZ()
                                + this.random.nextGaussian() * 1.3, 0.0, 0.0, 0.0);
            }
        } else {
            super.handleEntityEvent(p_34138_);
        }
    }

    public boolean canAttack(LivingEntity pTarget) {
        if (pTarget instanceof Dictator) {
            return this.isException();
        }
        return super.canAttack(pTarget);
    }

    public boolean isException() {
        return this.isCrazy();
    }

    public boolean addEffect(MobEffectInstance p_147208_, @Nullable Entity p_147209_) {
        return false;
    }

    public Collection<MobEffectInstance> getActiveEffects() {
        this.removeAllEffects();
        return super.getActiveEffects();
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public boolean isCrazy() {
        return this.getHealth() <= this.getMaxHealth() / 2;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new HorrorRangedAttackGoal(this, 30, 30));
        this.addMeleeAttackGoal(4, 1);
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 1));
        this.addBehaviorGoal(5, 1.0D, 10F);
        this.targetSelector.addGoal(6, new RedPlumsMobsHurtByTargetGoal(this, HeartOfHorror.class));
        this.addHostileGoal(6);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = AbstractRedPlumMob.createLivingAttributes();
        builder.add(Attributes.ARMOR, 10).add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.85)
                .add(Attributes.ATTACK_DAMAGE, 10).add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FOLLOW_RANGE, 50).add(Attributes.ATTACK_KNOCKBACK, 1);
        return builder;
    }

    public void performRangedAttack(double p_31450_, double p_31451_, double p_31452_) {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        double d3 = p_31450_ - d0;
        double d4 = p_31451_ - d1;
        double d5 = p_31452_ - d2;
        Venom venom = new Venom(BlueOceansEntities.VENOM.get(), this.getX(), this.getY(), this.getZ(), d3, d4,
                d5, this.level());
        venom.setOwner(this);
        venom.setPosRaw(d0, d1, d2);
        this.level().addFreshEntity(venom);
    }

    public void performRangedAttack(LivingEntity p_31459_, float f) {
        this.performRangedAttack(p_31459_.getX(), p_31459_.getY(), p_31459_.getZ());
    }

    static class HorrorRangedAttackGoal extends Goal {
        private final HeartOfHorror mob;
        private final RangedAttackMob rangedAttackMob;
        @Nullable
        private LivingEntity target;
        private int attackTime;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;

        public boolean canUse() {
            LivingEntity $$0 = this.mob.getTarget();
            if ($$0 != null && $$0.isAlive()) {
                if (this.mob.distanceToSqr(this.mob.getTarget()) < 15) {
                    return false;
                }
                this.target = $$0;
                return true;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return this.canUse() || this.target != null && this.target.isAlive() && !this.mob.getNavigation().isDone();
        }

        public void stop() {
            this.target = null;
            this.seeTime = 0;
            this.attackTime = -1;
            this.mob.setAggressive(false);
        }

        public void start() {
            this.mob.setAggressive(true);
        }

        public HorrorRangedAttackGoal(HeartOfHorror p_25768_, int p_25770_, float p_25771_) {
            this(p_25768_, p_25770_, p_25770_, p_25771_);
        }

        public HorrorRangedAttackGoal(HeartOfHorror p_25773_, int p_25775_, int p_25776_, float p_25777_) {
            this.attackTime = -1;
            this.rangedAttackMob = p_25773_;
            this.mob = p_25773_;
            this.attackIntervalMin = p_25775_;
            this.attackIntervalMax = p_25776_;
            this.attackRadius = p_25777_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            assert this.target != null;
            double $$0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean $$1 = this.mob.getSensing().hasLineOfSight(this.target);
            if ($$1) {
                ++this.seeTime;
            } else {
                this.seeTime = 0;
            }
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            if (--this.attackTime == 0) {
                if (!$$1) {
                    return;
                }
                float $$2 = (float) Math.sqrt($$0) / this.attackRadius;
                float $$3 = Mth.clamp($$2, 0.1F, 1.0F);
                this.rangedAttackMob.performRangedAttack(this.target, $$3);
                this.attackTime = Mth.floor($$2 * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
                this.mob.setShootTicks(this.attackTime);
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt($$0) / (double) this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
                this.mob.setShootTicks(this.attackTime);
            }
        }
    }
}
