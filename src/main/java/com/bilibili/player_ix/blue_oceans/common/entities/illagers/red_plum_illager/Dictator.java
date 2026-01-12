
package com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager;

import com.bilibili.player_ix.blue_oceans.api.magic.BOSpellType;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.github.player_ix.ix_api.api.ApiPose;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import java.util.List;

public class Dictator extends SpellcasterRedPlumIllager {
    private static final EntityDataAccessor<Boolean> SECOND_PHASE = SynchedEntityData.defineId(Dictator.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_COMMANDING = SynchedEntityData.defineId(Dictator.class, EntityDataSerializers.BOOLEAN);
    private int commandTicks;
    private  int commandCooldown;

    public Dictator(EntityType<? extends AbstractRedPlumMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        this.xpReward = 300;
        this.setMaxUpStep(1);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SECOND_PHASE, false);
        this.entityData.define(IS_COMMANDING, false);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.commandCooldown > 0) {
            --this.commandCooldown;
        }
    }

    public boolean wouldHaveOwner() {
        return false;
    }

    public boolean isCommanding() {
        return this.entityData.get(IS_COMMANDING) || this.commandTicks > 0;
    }

    protected void setIsCommanding(boolean b) {
        this.entityData.set(IS_COMMANDING, b);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new CastingSpellGoal());
        this.goalSelector.addGoal(2, new SummonFreakerGoal());
        this.goalSelector.addGoal(3, new RedPlumMonsterMeleeAttackGoal<>(
                this, 1.2, true, 2F));
        this.goalSelector.addGoal(4, new CommandGoal(32, 16, 32));
        this.addBehaviorGoal(5, 1.0, 12F);
        this.targetSelector.addGoal(6, new RedPlumsMobsHurtByTargetGoal(this));
        this.addHostileGoal(6);
    }

    public ApiPose getArmPose() {
        if (this.isCastingSpell()) {
            return ApiPose.SPELL_CASTING;
        }
        if (this.isAggressive()) {
            return ApiPose.ATTACKING;
        }
        if (this.isCommanding()) {
            return ApiPose.COMMANDING;
        }
        return ApiPose.CROSSED;
    }

    public void randomSummon() {
        ServerLevel level = (ServerLevel) this.level();
        Freak freak = BlueOceansEntities.FREAK.get().create(this.level());
        if (freak != null) {
            freak.moveTo(this.getX(3), this.getY(), this.getZ(3));
            freak.finalizeSpawn(level, level.getCurrentDifficultyAt(BlockPos.containing(this.getX(), this.getY(), this.getZ())),
                    MobSpawnType.MOB_SUMMONED, null, null);
            freak.setOwner(this);
            level.addFreshEntityWithPassengers(freak);
            if (!this.level().isClientSide()) {
                ((ServerLevel)freak.level()).sendParticles(ParticleTypes.LARGE_SMOKE, freak.getX(),
                        freak.getY(), freak.getZ(), 50, 0.1, 0.1, 0.1, 0.1);
            }
        }
    }

    public Dictator(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(BlueOceansEntities.DICTATOR.get(), world);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = AbstractRedPlumMob.createLivingAttributes();
        builder.add(Attributes.ATTACK_DAMAGE, 5).add(Attributes.ARMOR, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 300).add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(Attributes.FOLLOW_RANGE, 100).add(Attributes.ATTACK_KNOCKBACK, 1);
        return builder;
    }

    public SummonType getSummonType() {
        if (this.isHalfHealth()) {
            return SummonType.Freaker;
        } else {
            if (this.level().random.nextBoolean()) {
                return SummonType.Evoker;
            } else {
                return SummonType.Vindicator;
            }
        }
    }

    public enum SummonType {
        Freaker,
        Evoker,
        Vindicator
    }

    class SummonFreakerGoal extends UseSpellGoal {
        Dictator dictator = Dictator.this;

        protected void performSpellCasting() {
            if (!level().isClientSide)
                dictator.randomSummon();
        }

        protected int getCastingTime() {
            return 60;
        }

        protected int getCastingInterval() {
            return 600;
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EMPTY;
        }

        protected BOSpellType getSpell() {
            return BOSpellType.DARK;
        }

        public boolean canUse() {
            return super.canUse();
        }
    }

    private class CommandGoal extends UseSpellGoal {
        double x;
        double y;
        double z;
        Dictator dictator = Dictator.this;
        TargetingConditions illagers = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting()
                .range(32);
        TargetingConditions mobs = TargetingConditions.forCombat().range(32).ignoreInvisibilityTesting()
                .ignoreLineOfSight();

        public void tick() {
            super.tick();
            if (dictator.commandTicks > 0) {
                --dictator.commandTicks;
            }
        }

        CommandGoal(double d, double o, double u) {
            this.x = d;
            this.y = o;
            this.z = u;
        }

        public void start() {
            super.start();
            dictator.commandTicks = 100;
            dictator.setIsCommanding(true);
        }

        public void performSpellCasting() {
            List<Raider> list = dictator.level().getNearbyEntities(Raider.class, this.illagers, dictator, dictator.getBoundingBox()
                    .inflate(x, y, z));
            for (Raider raider : list) {
                raider.setTarget(dictator.getTarget());
            }
            List<AbstractRedPlumMob> ist = dictator.level().getNearbyEntities(AbstractRedPlumMob.class, this.mobs,
                    dictator, dictator.getBoundingBox().inflate(x, y, z));
            for (AbstractRedPlumMob mob : ist) {
                if (mob instanceof Dictator) {
                    return;
                }
                mob.setTarget(dictator.getTarget());
            }
            dictator.commandCooldown = 600;
            dictator.setIsCommanding(false);
            dictator.heal(10);
        }

        protected int getCastingTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return 300;
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EMPTY;
        }

        protected BOSpellType getSpell() {
            return BOSpellType.NONE;
        }
    }
}
