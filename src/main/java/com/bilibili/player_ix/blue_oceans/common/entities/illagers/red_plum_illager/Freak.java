
package com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager;

import com.bilibili.player_ix.blue_oceans.api.magic.BOSpellType;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.HeartOfHorror;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumWorm;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.github.player_ix.ix_api.api.ApiPose;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class Freak
extends SpellcasterRedPlumIllager {
    public AnimationState throwAni = new AnimationState();
    public Freak(EntityType<? extends AbstractRedPlumMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.xpReward = 25;
        this.setMaxUpStep(2f);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BlueOceansItems.FREAKY_AXE.get()));
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new CastingSpellGoal());
        this.goalSelector.addGoal(2, new SummonSpellGoal());
        this.goalSelector.addGoal(3, new RedPlumMonsterMeleeAttackGoal<>(this,
                1, true, 3));
        this.targetSelector.addGoal(3, new CrazyTargetGoal<>(this));
        this.addBehaviorGoal(4, 1.0, 10F);
        this.targetSelector.addGoal(5, new RedPlumsMobsHurtByTargetGoal(this,
                AbstractRedPlumMob.class));
        this.addHostileGoal(5);
    }

    public boolean isAlliedTo(@Nullable Entity p_32665_) {
        if (p_32665_ == null) {
            return false;
        } else if (p_32665_ == this) {
            return true;
        } else if (super.isAlliedTo(p_32665_)) {
            return true;
        } else if (p_32665_ instanceof HeartOfHorror heartOfHorror) {
            return this.isAlliedTo(heartOfHorror.getOwner());
        } else if (p_32665_ instanceof RedPlumWorm worm) {
            return this.isAlliedTo(worm.getOwner());
        } else {
            return false;
        }
    }

    public boolean wouldHaveOwner() {
        return false;
    }

    protected AttackCoolDownType getAttackCoolDownType() {
        return AttackCoolDownType.DEPEND_ON_HEALTH;
    }

    public float getHealAmount() {
        float $f;
        if (this.getKills() >= 10) {
            $f = (this.getKills() / 20f) + 3.5f;
            return $f;
        }
        return 3.5f;
    }

    protected float getDamageAfterArmorAbsorb(DamageSource ds, float a) {
        if (!ds.is(DamageTypes.GENERIC_KILL) && a > 15) {
            a = 15;
        }
        if (ds.is(DamageTypes.MAGIC) || ds.is(DamageTypes.INDIRECT_MAGIC)) {
            a *= 0.25f;
        }
        if (ds.is(DamageTypeTags.IS_PROJECTILE)) {
            a *= 0.5f;
        }
        return a;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        if (pSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    public boolean addEffect(MobEffectInstance p_147208_, @Nullable Entity p_147209_) {
        return false;
    }

    public SpellParticleType getSpellParticleType() {
        return super.getSpellParticleType();
    }

    public boolean isCrazy() {
        return this.getHealth() <= this.getMaxHealth() / 2;
    }

    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    public ApiPose getArmPose() {
        if (this.isCastingSpell()) {
            return ApiPose.SPELL_CASTING;
        }
        if (this.isAggressive()) {
            return ApiPose.ATTACKING;
        }
        return ApiPose.CROSSED;
    }

    public boolean doHurtTarget(Entity p_21372_) {
        if (this.getKills() >= 10) {
            if (p_21372_.isAlive() && p_21372_ instanceof LivingEntity lie) {
                lie.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 500, 1));
                if (lie instanceof Mob mob) {
                    mob.setTarget(null);
                }
                lie.hurt(this.damageSources().mobAttack(this), this.getHealAmount() +
                        (lie.getMaxHealth() / 100f));
                lie.playSound(SoundEvents.PLAYER_ATTACK_CRIT);
                double d = 0.75;
                if (!this.level().isClientSide) {
                    ((ServerLevel)this.level()).sendParticles(ParticleTypes.WITCH, lie.getX(), lie.getY(), lie.getZ(),
                            10, d, d, d, 0.1);
                }
            }
        }
        if (this.isAlive()) {
            this.setHealth(this.getHealth() + this.getHealAmount() - 3.4f + (this.isCrazy() ? 2f : 0.5f));
        }
        return super.doHurtTarget(p_21372_);
    }

    public void spawnAnim() {
        if (this.level().isClientSide) {
            for (int i =0; i < 30;i++) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(),
                        this.getRandom().nextGaussian() * 0.3, this.getRandom().nextGaussian() * 0.3,
                        this.getRandom().nextGaussian() * 0.3);
            }
        } else {
            this.level().broadcastEntityEvent(this, (byte) 20);
        }
    }

    public AnimationState getAnimationState(String string) {
        return this.throwAni;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = AbstractRedPlumMob.createMobAttributes();
        builder.add(Attributes.MAX_HEALTH, 160).add(Attributes.ARMOR, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.FOLLOW_RANGE, 72).add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 5).add(Attributes.ATTACK_KNOCKBACK, 1);
        return builder;
    }

    class SummonSpellGoal extends UseSpellGoal {
        Freak freak = Freak.this;

        private SummonSpellGoal() {
        }

        protected void performSpellCasting() {
            if (!freak.level().isClientSide) {
                ServerLevel level = (ServerLevel) freak.level();
                BlockPos pos = freak.blockPosition();
                RedPlumWorm worm = BlueOceansEntities.RED_PLUM_WORM.get().create(freak.level());
                if (worm != null) {
                    worm.setOwner(freak);
                    worm.setOwnerUUID(freak.getUUID());
                    worm.moveTo(freak.getX(), freak.getY(), freak.getZ());
                    worm.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
                    level.addFreshEntityWithPassengers(worm);
                }
                freak.heal(freak.getHealAmount() / 2f);
            }
        }

        protected int getCastingTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return 120;
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EMPTY;
        }

        protected BOSpellType getSpell() {
            return BOSpellType.DARK;
        }
    }

    static class CrazyTargetGoal<T extends Freak> extends NearestAttackableTargetGoal<LivingEntity> {
        protected final T freakager;

        public CrazyTargetGoal(T p_26060_) {
            super(p_26060_, LivingEntity.class, false);
            this.freakager = p_26060_;
        }

        public boolean canUse() {
            if (!this.freakager.isCrazy()) {
                return false;
            }
            if (this.targetMob instanceof AbstractRedPlumMob) {
                return false;
            }
            return super.canUse();
        }

        protected AABB getTargetSearchArea(double p_26069_) {
            return this.freakager.getBoundingBox()
                    .inflate(100);
        }
    }

    abstract class FreakagerGoal extends Goal {
        protected int useCoolDown;
        protected Freak freak = Freak.this;
        double x;
        double y;
        double z;

        FreakagerGoal(int coolDown) {
            super();
            this.useCoolDown = coolDown;
            this.x = freak.getX();
            this.y = freak.getY();
            this.z = freak.getZ();
        }

        public void tick() {
            super.tick();
            if (this.useCoolDown > 0) {
                --this.useCoolDown;
            }
        }

        protected abstract void cast();

        public boolean canUse() {
            if (freak.getTarget() == null) {
                return false;
            }
            return this.useCoolDown == 0;
        }

        public boolean canContinueToUse() {
            if (!this.canUse()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    class BombGoal extends FreakagerGoal {

        BombGoal(int coolDown) {
            super(400);
        }

        protected void cast() {
            LivingEntity target = freak.getTarget();
            if (target != null) {
                PrimedTnt tnt = new PrimedTnt(freak.level(), target.getX(), target.getY(), target.getZ(), freak);
                target.level().addFreshEntity(tnt);
            }
        }
    }
}
