
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl;

import com.bilibili.player_ix.blue_oceans.api.magic.BOSpellType;
import com.bilibili.player_ix.blue_oceans.api.mob.MobTypes;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.BOCastingSpellGoal;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.BOUseSpellGoal;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class RedPlumGirl
extends AbstractGirl
implements RedPlumMob {
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(),
            BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
    public RedPlumGirl(EntityType<RedPlumGirl> pGirl, Level level) {
        super(pGirl, level);
        this.setMaxUpStep(2f);
        this.xpReward = 560;
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new HealGoal(this));
        this.goalSelector.addGoal(3, new BOCastingSpellGoal(this));
        this.goalSelector.addGoal(3, new GirlRangedBowAttackGoal<>(this, 32f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, LivingEntity.class,
                100f));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this, RedPlumMob.class));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this,
                AbstractGolem.class, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this,
                Animal.class, false));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public MobTypes getMobTypes() {
        return MobTypes.NEUTRAL;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes()
                .add(Attributes.ARMOR, 12).add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FOLLOW_RANGE, 120)
                .add(Attributes.ATTACK_DAMAGE, 7).add(Attributes.ARMOR_TOUGHNESS, 2).
                add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.MAX_HEALTH, 560);
    }

    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return false;
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    protected float getDamageAfterArmorAbsorb(DamageSource ds, float a) {
        if (a > 20 && !(ds.is(DamageTypes.GENERIC_KILL))) {
            a = 20;
        }
        if (a > 60 && ds.is(DamageTypes.GENERIC_KILL)) {
            a = 60;
        }
        if (ds.is(DamageTypes.MAGIC) || ds.is(DamageTypes.INDIRECT_MAGIC) || ds.isIndirect()) {
            a *= 0.25f;
        }
        return a;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof RedPlumMob) {
            return false;
        }
        if (pSource.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        if (pSource.is(DamageTypeTags.WITHER_IMMUNE_TO)) {
            return false;
        }
        if (pSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    public SoundEvent getCastSound() {
        return BlueOceansSounds.RED_PLUM_GIRL_CAST_SPELL.get();
    }

    public void performRangedAttack(LivingEntity pTarget, float v) {
        ItemStack $$2 = this.getProjectile(this.getItemInHand(InteractionHand.MAIN_HAND));
        AbstractArrow $$3 = ProjectileUtil.getMobArrow(this, $$2, v);
        double $$4 = pTarget.getX() - this.getX();
        double $$5 = pTarget.getY(0.3333333333333333) - $$3.getY();
        double $$6 = pTarget.getZ() - this.getZ();
        double $$7 = Math.sqrt($$4 * $$4 + $$6 * $$6);
        if ($$3 instanceof Arrow arrow) {
            arrow.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, 1));
            arrow.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 0));
        }
        $$3.setBaseDamage(6);
        $$3.shoot($$4, $$5 + $$7 * 0.2f, $$6, 1.6f, 14 - this.level().getDifficulty().getId() * 4);
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat()
                * 0.4f + 0.8f));
        this.level().addFreshEntity($$3);
    }

    public SpellParticleType getSpellParticleType() {
        return SpellParticleType.INSTANT;
    }

    public ApiPose getArmPose() {
        if (!this.isAlive()) {
            return ApiPose.SPELL_AND_WEAPON;
        } else {
            if (this.isCastingSpell()) {
                return ApiPose.SPELL_AND_WEAPON;
            }
            if (this.isAggressive()) {
                return ApiPose.BOW_AND_ARROW;
            }
        }
        return ApiPose.NATURAL;
    }

    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.EMPTY;
    }

    protected @Nullable SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.PLAYER_HURT;
    }

    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }

    public void tick() {
        super.tick();
    }

    public boolean isLeftHanded() {
        return false;
    }

    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.level().addParticle(BlueOceansParticleTypes.BIG_RED_PLUM_INSTANT_SPELL.get(),
                    this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 0,
                    0, 0);
        } else {
            if (this.isAlive() && this.tickCount % 20 == 0) {
                this.heal(1f);
            }
            if (!this.getItemInHand(InteractionHand.MAIN_HAND).is(Items.BOW)) {
                this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
            }
        }
    }

    private static class HealGoal extends BOUseSpellGoal {
        public HealGoal(RedPlumGirl girl) {
            super(girl);
        }

        public void castSpell() {
            RedPlumGirl girl = this.convert();
            girl.heal(10f);
        }

        protected int getCastingTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return 400;
        }

        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            }
            if (this.mob.getHealth() >= this.mob.getMaxHealth() - 10F) {
                return false;
            }
            return super.canUse();
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EMPTY;
        }

        protected BOSpellType getSpell() {
            return BOSpellType.RED_PLUM;
        }
    }

    private static class GirlRangedBowAttackGoal<T extends RedPlumGirl> extends Goal {
        public final T mob;
        public int attackIntervalMin;
        public final float attackRadiusSqr;
        public int attackTime = -1;
        public int seeTime;
        public boolean strafingClockwise;
        public boolean strafingBackwards;
        public int strafingTime = -1;

        public GirlRangedBowAttackGoal(T pMob, float pRange) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.mob = pMob;
            this.attackRadiusSqr = pRange * pRange;
        }

        public boolean canUse() {
            return this.mob.getTarget() != null && this.isHoldingBow() && !this.mob.isCastingSpell();
        }

        protected boolean isHoldingBow() {
            return this.mob.isHolding((is) -> is.getItem() instanceof BowItem);
        }

        public boolean canContinueToUse() {
            return (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingBow() && !this.mob.isCastingSpell();
        }

        public void start() {
            this.mob.setAggressive(true);
        }

        public void stop() {
            this.mob.setAggressive(false);
            this.seeTime = 0;
            this.attackTime = -1;
            if (this.mob.getTarget() == null) {
                 this.mob.stopUsingItem();
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null) {
                double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
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
                if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
                    this.mob.getNavigation().stop();
                    ++this.strafingTime;
                } else {
                    this.mob.getNavigation().moveTo(livingentity,1);
                    this.strafingTime = -1;
                }
                if (this.strafingTime >= 20) {
                    if ((double)this.mob.getRandom().nextFloat() < 0.3) {
                        this.strafingClockwise = !this.strafingClockwise;
                    }
                    if ((double)this.mob.getRandom().nextFloat() < 0.3) {
                        this.strafingBackwards = !this.strafingBackwards;
                    }
                    this.strafingTime = 0;
                }
                if (this.strafingTime > -1) {
                    if (d0 > (double)(this.attackRadiusSqr * 0.75F)) {
                        this.strafingBackwards = false;
                    } else if (d0 < (double)(this.attackRadiusSqr * 0.25F)) {
                        this.strafingBackwards = true;
                    }
                    this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                    Entity entity = this.mob.getControlledVehicle();
                    if (entity instanceof Mob mobs) {
                        mobs.lookAt(livingentity, 300.0F, 30.0F);
                    }
                    this.mob.lookAt(livingentity, 300.0F, 30.0F);
                } else {
                    this.mob.getLookControl().setLookAt(livingentity.getX(), livingentity.getY() + 1, livingentity.getZ());
                }
                if (!this.mob.isCastingSpell()) {
                    if (this.mob.isUsingItem()) {
                        if (!flag && this.seeTime < -60) {
                            this.mob.stopUsingItem();
                        } else if (flag) {
                            int i = (this.mob.getTicksUsingItem());
                            if (i >= 20 && !this.mob.isCastingSpell()) {
                                this.mob.stopUsingItem();
                                this.mob.performRangedAttack(livingentity, (BowItem.getPowerForTime(i) / 1.5f));
                            }
                            this.attackTime = attackIntervalMin;
                        }
                    } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                        this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, (item) -> item instanceof BowItem));
                    }
                }
            }
        }
    }

    private static class ControlPlumMobsGoal extends BOUseSpellGoal {
        public ControlPlumMobsGoal(RedPlumGirl finder) {
            super(finder);
        }

        protected void castSpell() {
            RedPlumGirl girl = this.convert();
            List<LivingEntity> list = girl.level().getEntitiesOfClass(LivingEntity.class, girl.getBoundingBox()
                    .inflate(64), entity -> entity instanceof RedPlumMob);
            for (LivingEntity livingEntity : list) {
                RedPlumMob mobs = (RedPlumMob)livingEntity;
                mobs.setControlledByGirl(true);
            }
        }

        protected int getCastingTime() {
            return 40;
        }

        protected int getCastingInterval() {
            return 600;
        }

        protected SoundEvent getSpellPrepareSound() {
            return BlueOceansSounds.RED_PLUM_GIRL_CAST_SPELL.get();
        }

        protected BOSpellType getSpell() {
            return BOSpellType.RED_PLUM;
        }
    }
}
