
package com.bilibili.player_ix.blue_oceans.common.entities.illagers;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.api.mob.RedPlumMob;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NaturalEnvoy
extends ModSpellcasterIllager {
    @Nullable
    private Sheep wololoTarget;

    public NaturalEnvoy(EntityType<? extends NaturalEnvoy> type, Level level) {
        super(type, level);
    }

    public int getExperienceReward() {
        return 10;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new NaturalEnvoyCastingSpellGoal());
        this.goalSelector.addGoal(2, new AttackSpellGoal());
        this.goalSelector.addGoal(2, new WololoGoal());
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 10f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractGolem.class,
                false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, LivingEntity.class,
                false, ling -> ling instanceof RedPlumMob));
        super.registerGoals();
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }

    public void damage() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8));
        list.forEach(lie -> {
            if (!(lie instanceof Raider) && !(lie instanceof Vex) && !(lie instanceof Animal)) {
                lie.hurt(this.damageSources().indirectMagic(this, this), 8f);
                this.knockBack(lie);
                double d = 0.75;
                if (!this.level().isClientSide) {
                    ServerLevel serverLevel = (ServerLevel)this.level();
                    serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, lie.getX(), lie.getY()
                            + 1, lie.getZ(), 10, d, d, d, 0.1);
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY()
                            + 1, this.getZ(), 50, d, d, d, 0.15);
                }
            }
        });
    }

    public void knockBack(Entity pEntity) {
        double d0 = pEntity.getX() - this.getX();
        double d1 = pEntity.getZ() - this.getZ();
        double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
        pEntity.push(d0 / d2 * 4.0, 0.2, d1 / d2 * 4.0);
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ILLUSIONER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ILLUSIONER_DEATH;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public NaturalEnvoy(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(BlueOceansEntities.NATURAL_ENVOY.get(), level);
    }

    public void applyRaidBuffs(int i, boolean b) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
    }

    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
    }

    @Nullable
    private Sheep getWololoTarget() {
        return this.wololoTarget;
    }

    private void setWololoTarget(@Nullable Sheep sheep) {
        this.wololoTarget = sheep;
    }

    public IllagerArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return IllagerArmPose.SPELLCASTING;
        }
        return IllagerArmPose.CROSSED;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return NaturalEnvoy.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 72).add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.MAX_HEALTH, 75).add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(Attributes.ARMOR, 4);
    }

    private class SummonSpellGoal
    extends UseSpellGoal {

        protected void castSpell() {
        }

        protected SpellType getSpell() {
            return SpellType.NATURAL;
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        protected int getCastingTime() {
            return 60;
        }

        protected int getCastingInterval() {
            return 430;
        }
    }

    private class AttackSpellGoal
    extends UseSpellGoal {

        @Override
        protected void castSpell() {
            ((NaturalEnvoy)illager).damage();
        }

        @Override
        protected SpellType getSpell() {
            return SpellType.DARK;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }

        @Override
        protected int getCastingTime() {
            return 30;
        }

        @Override
        public boolean canUse() {
            if (illager.getTarget() != null && illager.distanceToSqr(illager.getTarget()) > 20) {
                return false;
            }
            return super.canUse();
        }

        @Override
        protected int getCastingInterval() {
            return 90;
        }
    }

    private class WololoGoal
    extends UseSpellGoal {
        private final TargetingConditions wololoTargeting = TargetingConditions.forNonCombat().range(16.0)
                .selector((p_32710_) -> ((Sheep) p_32710_).getColor() != DyeColor.GREEN);

        protected void castSpell() {
            Sheep sheep = NaturalEnvoy.this.getWololoTarget();
            if (sheep != null && sheep.isAlive()) {
                sheep.setColor(DyeColor.GREEN);
            }
        }

        public void stop() {
            super.stop();
            NaturalEnvoy.this.setWololoTarget(null);
        }

        @Override
        protected SpellType getSpell() {
            return SpellType.NATURAL;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_WOLOLO;
        }

        @Override
        protected int getCastingTime() {
            return 30;
        }

        @Override
        protected int getCastingInterval() {
            return 90;
        }

        public boolean canUse() {
            if (illager.getTarget() != null) {
                return false;
            } else if (illager.tickCount < this.nextAttackTickCount) {
                return false;
            } else if (illager.isCastingSpell()) {
                return false;
            } else {
                List<Sheep> list = illager.level().getNearbyEntities(Sheep.class, this.wololoTargeting, illager, illager
                        .getBoundingBox().inflate(16.0, 4.0, 16.0));
                if (list.isEmpty()) {
                    return false;
                } else {
                    ((NaturalEnvoy)illager).setWololoTarget(list.get(illager.getRandom().nextInt(list.size())));
                    return true;
                }
            }
        }

        public boolean canContinueToUse() {
            return NaturalEnvoy.this.getWololoTarget() != null && this.attackWarmupDelay > 0;
        }
    }

    private class NaturalEnvoyCastingSpellGoal
    extends CastingSpellGoal {
        NaturalEnvoyCastingSpellGoal() {
        }

        public void tick() {
            Sheep sheep = NaturalEnvoy.this.getWololoTarget();
            if (NaturalEnvoy.this.getTarget() != null) {
                NaturalEnvoy.this.lookControl.setLookAt(NaturalEnvoy.this.getTarget());
            } else if (sheep != null) {
                NaturalEnvoy.this.lookControl.setLookAt(sheep);
            }
        }
    }
}
