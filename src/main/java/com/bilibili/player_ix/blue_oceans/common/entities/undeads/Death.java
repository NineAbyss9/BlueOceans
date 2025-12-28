
package com.bilibili.player_ix.blue_oceans.common.entities.undeads;

import com.bilibili.player_ix.blue_oceans.api.ai.goal.BOAttackTargetGoal;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.Venom;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.github.player_ix.ix_api.api.mobs.Ownable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.Nullable;

public class Death
extends UndeadBoss
implements RangedAttackMob {
    private int clientRenderShadowTick;
    private final Vec3[] clientShadowPos;
    public Death(EntityType<? extends Death> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 100;
        this.clientShadowPos = new Vec3[2];
        for (int i =0; i < clientShadowPos.length;i++) {
            this.clientShadowPos[i] = Vec3.ZERO;
        }
    }

    @SuppressWarnings("unused")
    public Death(PlayMessages.SpawnEntity spawnEntity, Level w) {
        this(BlueOceansEntities.DEATH.get(), w);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            for (int t = 0;t < 2;++t) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(1), this.getRandomY()
                        , this.getRandomZ(1), 0, 0, 0);
            }
        }
    }

    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            if (this.isMoving() && this.clientRenderShadowTick <= 0) {
                this.clientRenderShadowTick = 40;
            }
            if (this.clientRenderShadowTick == 40)
                this.clientShadowPos[1] = this.getClientShadowOffset();
            if (this.clientRenderShadowTick == 20)
                this.clientShadowPos[0] = this.getClientShadowOffset();
            if (this.clientRenderShadowTick > 0) {
                --this.clientRenderShadowTick;
            }
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CastingSpellGoal());
        this.goalSelector.addGoal(3, new DeathAttackSpellGoal());
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, LivingEntity.class, 20f));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        RandomStrollGoal goal = new RandomStrollGoal(this, 0.8);
        goal.setInterval(3);
        this.goalSelector.addGoal(4, goal);
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new BOAttackTargetGoal(this, false));
        super.registerGoals();
    }

    public void summonFangs(LivingEntity lie) {
        EvokerFangs fangs = new EvokerFangs(EntityType.EVOKER_FANGS, lie.level());
        fangs.setOwner(this);
        fangs.moveTo(lie.getX(), lie.getY(), lie.getZ());
        lie.level().addFreshEntity(fangs);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.STRAY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.STRAY_DEATH;
    }

    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
    }

    protected float getDamageAfterArmorAbsorb(DamageSource ds, float var) {
        if (ds.is(DamageTypes.MAGIC) || ds.is(DamageTypes.INDIRECT_MAGIC)) {
            var *= 0.25f;
        }
        if (var > 20 && !ds.is(DamageTypes.GENERIC_KILL)) {
            var = 20;
        }
        if (ds.is(DamageTypeTags.IS_PROJECTILE)) {
            var *= 0.5f;
        }
        return var;
    }

    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_.getEntity() instanceof Ownable mob && mob.getOwner() == this) {
            return false;
        }
        if (p_21016_.is(DamageTypeTags.IS_FALL) || p_21016_.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
            return false;
        }
        return super.hurt(p_21016_, p_21017_);
    }

    public Vec3[] getClientShadowPos() {
        return clientShadowPos;
    }

    public Vec3 getClientShadowOffset() {
        return new Vec3(this.xOld - this.getX(), this.yOld - this.getY(), this.zOld - this.getZ());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Death.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 72).add(Attributes.MAX_HEALTH, 320)
                .add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.ARMOR, 10)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.85)
                .add(Attributes.ATTACK_DAMAGE, 7);
    }

    public void performRangedAttack(LivingEntity livingEntity, float v) {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        double d3 = livingEntity.getX() - d0;
        double d4 = livingEntity.getY() - d1;
        double d5 = livingEntity.getZ() - d2;
        Venom venom = new Venom(BlueOceansEntities.VENOM.get(), this.getX(), this.getY(), this.getZ(),
                d3, d4, d5, this.level());
        venom.setOwner(this);
        venom.setPosRaw(d0, d1, d2);
        this.level().addFreshEntity(venom);
    }

    public BossPose getBossPose() {
        if (this.isCastingSpell()) {
            return BossPose.CASTING_SPELL;
        }
        return BossPose.NATURAL;
    }

    public boolean shouldRenderShadow() {
        return this.clientRenderShadowTick > 0;
    }

    public boolean isMoving() {
        return this.walkAnimation.speed() >= 0.1F;
    }

    public SoundEvent getCastingSoundEvent() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    class CastingSpellGoal
    extends Goal {

        public boolean canUse() {
            return Death.this.spellTicks > 0;
        }

        public void start() {
            super.start();
            Death.this.navigation.stop();
        }

        public void tick() {
            super.tick();
            if (Death.this.getTarget() != null) {
                LivingEntity lie = Death.this.getTarget();
                Death.this.getLookControl().setLookAt(lie.getX(), lie.getY() + 1, lie.getZ());
            }
        }
    }

    abstract class DeathGoal
    extends Goal {
        protected Death death = Death.this;
        protected int coolDown;
        protected int attackWarmupDelay;
        protected int spellTicks;

        DeathGoal(int CoolDown, int spellTicks) {
            super();
            this.coolDown = CoolDown;
            this.spellTicks = spellTicks;
        }

        int getCastingTime() {
            return this.spellTicks;
        }

        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.castSpell();
                death.playSound(death.getCastingSoundEvent(), 1f, 1f);
                this.stop();
            }
        }

        public boolean canUse() {
            if (death.isCastingSpell()) {
                return false;
            }
            if (death.getTarget() == null) {
                return false;
            }
            return death.tickCount >= this.coolDown;
        }

        int getAttackWarmupDelay() {
            return 20;
        }

        public boolean canContinueToUse() {
            return this.attackWarmupDelay > 0 && death.getTarget() != null;
        }

        int getCoolDown() {
            return this.coolDown;
        }

        @Nullable
        abstract SoundEvent getSpellPrepareSound();

        abstract void castSpell();

        public void start() {
            this.attackWarmupDelay = this.adjustedTickDelay(this.getAttackWarmupDelay());
            death.spellTicks = this.getCastingTime();
            this.coolDown = death.tickCount + this.getCoolDown();
            SoundEvent $$0 = this.getSpellPrepareSound();
            if ($$0 != null) {
                death.playSound($$0, 1f, 1f);
            }
        }

        public void stop() {
            super.stop();
        }
    }

    class DeathAttackSpellGoal
    extends DeathGoal {
        DeathAttackSpellGoal() {
            super(400, 40);
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EMPTY;
        }

        protected void castSpell() {
            if (death.getTarget() != null) {
                death.summonFangs(death.getTarget());
            }
        }
    }
}
