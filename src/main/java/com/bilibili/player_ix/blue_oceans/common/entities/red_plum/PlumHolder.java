
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.NineAbyss9.ix_api.api.annotation.ServerOnly;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import org.NineAbyss9.annotation.doc.Message;
import org.NineAbyss9.math.MathSupport;
import org.NineAbyss9.util.ValueHolder;

import javax.annotation.Nullable;
import java.util.Objects;

public class PlumHolder
extends RedPlumMonster {
    public static final EntityDataAccessor<String> DATA_SPAWN_ID;
    public static final EntityDataAccessor<Boolean> DATA_DESPAWN;
    public static final EntityDataAccessor<Integer> DATA_DESPAWN_ID;
    public static final EntityDataAccessor<Integer> DATA_TICK;
    public static final String DEFAULT_SPAWN_MOB = "blue_oceans:red_plum_human";
    public AnimationState idle = new AnimationState();
    public AnimationState spawn = new AnimationState();
    public AnimationState despawn = new AnimationState();
    private int trueDeathTick;
    public PlumHolder(EntityType<? extends PlumHolder> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SPAWN_ID, DEFAULT_SPAWN_MOB);
        this.entityData.define(DATA_DESPAWN, false);
        this.entityData.define(DATA_DESPAWN_ID, -1);
        this.entityData.define(DATA_TICK, 0);
    }

    public void aiStep() {
        super.aiStep();
        if (this.isAlive()) {
            this.setTick(this.getTick() + 1);
            this.noPhysics = this.getTick() < 60 || this.getTick() >= 120;
            this.setNoGravity(this.noPhysics);
            if (this.level().isClientSide) {
                this.idle.startIfStopped(tickCount);
                if (despawn() && this.getTick() == 10)
                    this.spawn.start(tickCount);
                if (this.getTick() < 60 || this.getTick() > 120) {
                    ParticleUtil.addBlockParticle(level(), blockPosition().below(), x(), y(), z());
                }
                if (this.getTick() == 70) {
                    if (despawn()) {
                        this.spawn.stop();
                        this.despawn.start(tickCount);
                    }
                    else
                        this.spawn.start(tickCount);
                }
            } else {
                if (this.getTick() < 60) {
                    this.setDeltaMovement(0d, 0.037d, 0d);
                }
                if (this.getTick() > 120)
                    this.setDeltaMovement(0d, -0.02d, 0d);
                if (this.getTick() == 80) {
                    if (this.despawn()) {
                        this.despawnMob();
                    } else {
                        this.spawnMob();
                    }
                }
                if (this.getTick() >= 180) {
                    this.discard();
                }
            }
        }
    }

    @Nullable
    public EntityType<?> getSpawnEntityType() {
        ResourceLocation location = new ResourceLocation(this.getSpawnId());
        return ForgeRegistries.ENTITY_TYPES.getValue(location);
    }

    @Nullable
    public Entity getSpawnMob() {
        return Objects.requireNonNullElseGet(this.getSpawnEntityType(), BlueOceansEntities.RED_PLUM_HUMAN).create(level());
    }

    @ServerOnly
    public void spawnMob() {
        Entity entity = this.getSpawnMob();
        if (entity != null) {
            entity.moveTo(this.position().add(0d,0.01d,0d));
            if (entity instanceof Mob)
                ForgeEventFactory.onFinalizeSpawn((Mob)entity, serverLevel(), level().getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.MOB_SUMMONED, null, null);
            this.level().addFreshEntity(entity);
        }
    }

    protected void tickDeath() {
        if (++this.trueDeathTick >= 20) {
            this.discard();
        }
    }

    public void remove(RemovalReason pReason) {
        if (despawn()) this.despawnMob();
        super.remove(pReason);
    }

    public boolean shouldAttackOtherMobs() {
        return false;
    }

    public boolean despawn() {
        return this.entityData.get(DATA_DESPAWN);
    }

    public void setDespawn(int id) {
        this.entityData.set(DATA_DESPAWN, id > -1);
        this.entityData.set(DATA_DESPAWN_ID, id);
    }

    public void despawnMob() {
        var entity = this.getDespawnMob();
        if (entity != null)
            entity.discard();
    }

    @Nullable
    public Entity getDespawnMob() {
        return this.level().getEntity(this.getDespawnId());
    }

    public String getSpawnId() {
        return this.entityData.get(DATA_SPAWN_ID);
    }

    public void setSpawn(String id) {
        this.entityData.set(DATA_SPAWN_ID, id);
    }

    /**@param pIndex the index of the map.*/
    public void fromList(int pIndex) {
        var list = RedPlumUtil.STRING_MAP.get(pIndex);
        this.setSpawn(list.get(MathSupport.random.nextInt(list.size())));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason,
                                        @Nullable SpawnGroupData pData, @Nullable CompoundTag pTag) {
        if (pReason == MobSpawnType.SPAWN_EGG)
            this.moveTo(position().add(0d, -2d, 0d));
        if (DEFAULT_SPAWN_MOB.equals(this.getSpawnId())) {
            this.fromList(1);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pData, pTag);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.despawn())
            return false;
        if (this.getTick() < 60 || this.getTick() > 140)
            return false;
        return super.hurt(pSource, pAmount);
    }

    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource.is(DamageTypes.IN_WALL))
            return true;
        if (pSource.is(DamageTypeTags.IS_FIRE))
            return true;
        return super.isInvulnerableTo(pSource);
    }

    protected AABB makeBoundingBox()
    {
        if (this.despawn()) {
            if (this.getDespawnMob() != null)
                return this.getDespawnMob().getType().getDimensions().makeBoundingBox(this.position());
        } else
            return this.getSpawnMob().getType().getDimensions().makeBoundingBox(this.position());
        return super.makeBoundingBox();
    }

    public int getDespawnId() {
        return this.entityData.get(DATA_DESPAWN_ID);
    }

    public int getTick() {
        return this.entityData.get(DATA_TICK);
    }

    public void setTick(int tick) {
        this.entityData.set(DATA_TICK, tick);
    }

    public static void despawn(AbstractRedPlumMob pMob, Level pLevel) {
        PlumHolder holder = BlueOceansEntities.PLUM_HOLDER.get().create(pLevel);
        if (holder != null) {
            holder.setDespawn(pMob.getId());
            holder.moveTo(pMob.position().add(0, -2, 0));
            pLevel.addFreshEntity(holder);
            holder.refreshDimensions();
        }
    }

    public static void spawn(Level pLevel, @Message("don't below()") BlockPos pPos, int index) {
        PlumHolder holder = BlueOceansEntities.PLUM_HOLDER.get().create(pLevel);
        if (holder != null) {
            holder.moveTo(pPos.below().below(), 0, 0);
            holder.fromList(index);
            pLevel.addFreshEntity(holder);
            holder.refreshDimensions();
        }
    }

    protected ResourceLocation getDefaultLootTable() {
        return ValueHolder.nullToOther(this.getSpawnEntityType(), this.getType()).getDefaultLootTable();
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    protected void registerGoals() {}

    public void registerBehaviors() {}

    public void push(double pX, double pY, double pZ) {}

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.ARMOR, 2)
                .add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.ATTACK_DAMAGE, 1);
    }

    static {
        DATA_SPAWN_ID = SynchedEntityData.defineId(PlumHolder.class, EntityDataSerializers.STRING);
        DATA_DESPAWN = SynchedEntityData.defineId(PlumHolder.class, EntityDataSerializers.BOOLEAN);
        DATA_DESPAWN_ID = SynchedEntityData.defineId(PlumHolder.class, EntityDataSerializers.INT);
        DATA_TICK = SynchedEntityData.defineId(PlumHolder.class, EntityDataSerializers.INT);
    }
}
