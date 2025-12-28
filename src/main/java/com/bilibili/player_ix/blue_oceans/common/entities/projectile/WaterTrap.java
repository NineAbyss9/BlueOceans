
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.api.annotation.MayBeNull;
import com.bilibili.player_ix.blue_oceans.util.MobUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class WaterTrap
extends Entity
implements TraceableEntity {
    private static Predicate<Entity> NO_RAVAGER_AND_ALIVE;
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 24;
    private boolean clientSideAttackStarted;
    @MayBeNull
    private LivingEntity owner;
    @MayBeNull
    private UUID ownerUUID;

    public WaterTrap(Level level, double v, double $$2, double $$3, float $$4, int $$5, LivingEntity $$6) {
        this(BlueOceansEntities.WATER_TRAP.get(), level);
        this.warmupDelayTicks = $$5;
        this.setOwner($$6);
        this.setYRot($$4 * 57.295776f);
        this.setPos(v, $$2, $$3);
        NO_RAVAGER_AND_ALIVE = entity -> {
            if (entity instanceof LivingEntity livingEntity)
                return MobUtil.canHurt(livingEntity, this);
            else
                return false;
        };
    }

    public WaterTrap(EntityType<WaterTrap> type, Level world) {
        super(type, world);
    }

    protected void defineSynchedData() {
    }

    public void setOwner(@Nullable LivingEntity $$0) {
        this.owner = $$0;
        this.ownerUUID = $$0 == null ? null : $$0.getUUID();
    }

    @MayBeNull
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel serverLevel &&
                (serverLevel.getEntity(this.ownerUUID)) instanceof LivingEntity livingEntity) {
            this.owner = livingEntity;
        }
        return this.owner;
    }

    protected void readAdditionalSaveData(CompoundTag $$0) {
        this.warmupDelayTicks = $$0.getInt("Warmup");
        if ($$0.hasUUID("Owner")) {
            this.ownerUUID = $$0.getUUID("Owner");
        }
    }

    public void setLifeTicks(int nt) {
        this.lifeTicks = nt;
    }

    protected void addAdditionalSaveData(CompoundTag $$0) {
        $$0.putInt("Warmup", this.warmupDelayTicks);
        if (this.ownerUUID != null) {
            $$0.putUUID("Owner", this.ownerUUID);
        }
    }

    public void knockback (LivingEntity lie) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        lie.push(x / z * 0, 1, y / z * 0);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.getOwner() != null) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX(), this.getY() + 0.5,
                        this.getZ(), 0.3, 0.3, 0.8);
            }
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (this.lifeTicks == 14) {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX(), this.getY() + 0.5,
                            this.getZ(), 0.9, 0.3, 0.9);
                }
            }
        } else if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -8) {
                List<LivingEntity> $$7 = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()
                        .inflate(3, 0, 3), NO_RAVAGER_AND_ALIVE);
                for (LivingEntity $$8 : $$7) {
                if (!($$8 instanceof AbstractIllager)) {
                        this.dealDamageTo($$8);
                        this.knockback($$8);
                    }
                }
            }
            if (!this.sentSpikeEvent) {
                this.level().broadcastEntityEvent(this, (byte) 4);
                this.sentSpikeEvent = true;
            }
            if (--this.lifeTicks < 0) {
                this.discard();
            }
        }
    }

    public void dealDamageTo(LivingEntity pTarget) {
        LivingEntity $$1 = this.getOwner();
        if (pTarget instanceof  Player player && player.isCreative()) {
            return;
        }
        if (!pTarget.isAlive() || pTarget.isInvulnerable() || pTarget == $$1) {
            return;
        }
        if ($$1 == null) {
            pTarget.hurt(this.damageSources().magic(), 10f);
        }
        if ($$1 != null) {
            pTarget.hurt(this.damageSources().indirectMagic(this, $$1), 10.0f);
        }
    }

    public void handleEntityEvent(byte pId) {
        super.handleEntityEvent(pId);
        if (pId == 4) {
            this.clientSideAttackStarted = true;
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.FISHING_BOBBER_SPLASH,
                        this.getSoundSource(), 1.0f, this.random.nextFloat() * 0.2f + 0.85f,
                        false);
            }
        }
    }
}
