
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.api.mob.Immobile;
import com.bilibili.player_ix.blue_oceans.common.blocks.plum.RedPlumCatalyst;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.NineAbyss9.ix_api.api.annotation.ServerOnly;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import com.github.NineAbyss9.ix_api.util.Maths;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.math.AbyssMath;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class PlumBuilder
extends RedPlumMonster
implements IFlagMob, IAnimatedMob, IPlumSpreader, Immobile {
    private static final EntityDataAccessor<Boolean> DATA_BUILDING;
    private static final EntityDataAccessor<Integer> DATA_AGE;
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    private static final int MAX_AGE = 8;
    private static final int NEXT_AGE = Maths.minuteToTick(2);
    private static final int SPREAD_COOLDOWN = Maths.toTick(30);
    private int lostTargetTime;
    private int spreadTimer;
    private int tryCount;
    public AnimationState idle = new AnimationState();
    public AnimationState spread = new AnimationState();
    public PlumBuilder(EntityType<? extends PlumBuilder> type, Level level) {
        super(type, level);
        this.lostTargetTime = 120;
        this.setHealth(this.getHealthByAge());
        this.refreshDimensions();
        this.setMaxUpStep(1.4F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BUILDING, false);
        this.entityData.define(DATA_AGE, 0);
        this.entityData.define(DATA_FLAGS, 0);
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (this.lostTargetTime <= 0 && this.isEmptyTarget() && !this.isBuilding()) {
                this.startBuilding();
            }
            if (this.isBuilding()) {
                if (!isMaxAge() && this.tickCount % NEXT_AGE == 0) {
                    this.increaseAge();
                    if (this.getAge() > 4) {
                        this.sendNewBuilder();
                    }
                }
                if (this.spreadTimer == 15) {
                    this.setFlag(1);
                }
                if (this.spreadTimer <= 0) {
                    this.puffSound();
                    this.spread();
                    if (PlumFactory.checkPlums(level(), this.getBoundingBox().inflate(16), 20))
                        for (int counterNA = 0;counterNA < this.getSpawnCountByAge();counterNA++)
                            this.spawnPlums();
                    else {
                        if (this.isMaxAge()) {
                            this.sendNewBuilder();
                        }
                        if (this.getHealth() < this.getHealthByAge()) {
                            List<AbstractRedPlumMob> mobs = this.level().getEntitiesOfClass(AbstractRedPlumMob.class,
                                    this.getBoundingBox().inflate(16), pMob ->
                                            pMob.getLevel() < 2 && pMob.getOwner() != this && pMob.getKills() > 0);
                            if (!mobs.isEmpty())
                                mobs.stream().findAny().ifPresent(pMob ->
                                {
                                    pMob.setOwner(this);
                                    pMob.setTargetPos(this.position());
                                    pMob.setPlumFlag(1);
                                });
                        }
                    }
                    this.setFlag(0);
                    this.spreadTimer = SPREAD_COOLDOWN;
                }
            } else if (!this.isEmptyTarget()) {
                if (this.tickCount % 40 == 0 && this.navigation.isDone()) {
                    if (this.moveToTargetPos())
                        tryCount = 0;
                    else
                        tryCount++;
                }
                if (this.tryCount > 5) {
                    this.startBuilding();
                    return;
                }
                Vec3 vec3 = this.getTargetPos();
                if (Math.abs(this.x() - vec3.x) < 3 && Math.abs(this.z() - vec3.z) < 3) {
                    this.startBuilding();
                }
            }
        }
        if (this.level().isClientSide && this.getFlag() == 0)
            this.idle.startIfStopped(tickCount);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.lostTargetTime > 0)
            --this.lostTargetTime;
        if (this.spreadTimer > 0)
            --this.spreadTimer;
        if (this.tickCount % 20 == 0 && (this.getAge() >= 2 || this.getKills() > 10)) {
            heal(this, 0.5F);
        }
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement());
        } else
            super.travel(pTravelVector);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WallClimberNavigation(this, pLevel);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey) && this.level().isClientSide) {
            int i = this.getFlag();
            if (i == 1) {
                this.stopAllAnimations();
                this.spread.startIfStopped(tickCount);
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    public boolean moveToTargetPos() {
        Vec3 vec3 = this.getTargetPos();
        return this.navigation.moveTo(vec3.x, vec3.y, vec3.z, 0.8);
    }

    public void startBuilding() {
        this.setTargetPos(this.position());
        this.setBuilding(true);
        this.moveControl.setWantedPosition(x(), y(), z(), 0);
        this.navigation.stop();
        this.setXRot(0);
        this.setYRot(0);
    }

    public void puffSound() {
        this.playSound(BlueOceansSounds.PUFF.get());
    }

    @ServerOnly
    public void spread() {
        this.spreadParticle();
        int plumBuilder$spread$int = 0;
        while (plumBuilder$spread$int < this.getAge() * 3) {
            RedPlumCatalyst.spreadPlum(serverLevel(), this.randomSpreadCenter());
            plumBuilder$spread$int++;
        }
    }

    @ServerOnly
    private void spreadParticle() {
        ParticleUtil.sendParticles(serverLevel(), DustParticleOptions.REDSTONE, position(),
                20, 1, 1, 1, 0.01);
    }

    private BlockPos randomSpreadCenter() {
        return this.blockPosition().below().offset(AbyssMath.random(6 * this.getAge()), 0,
                AbyssMath.random(6 * this.getAge()));
    }

    //private Vec3 getJetEnginePos() {
    //    return this.position().add(-0.2, 0.4, -0.2);
    //}

    public void anotherJoin(AbstractRedPlumMob pMob) {
        heal(this, (float)pMob.getKills());
        this.setKills(this.getKills() + pMob.getKills());
        pMob.discard();
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean flag = super.hurt(pSource, pAmount);
        if (flag && this.level().getEntitiesOfClass(AbstractRedPlumMob.class,
                this.getBoundingBox().inflate(8)).size() < 4) {
            this.protectSelf(pAmount);
            return true;
        }
        return false;
    }

    protected float getDamageAfterArmorAbsorb(DamageSource pDamageSource, float pDamageAmount) {
        return super.getDamageAfterArmorAbsorb(pDamageSource, Math.min(pDamageAmount,
                this.getMaxHealth() / 4F));
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Building", this.isBuilding());
        tag.putInt("Age", this.getAge());
        tag.putInt("SpreadTimer", this.spreadTimer);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setAge(tag.getInt("Age"));
        this.spreadTimer = tag.getInt("SpreadTimer");
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason, SpawnGroupData pData, CompoundTag pTag) {
        if (pReason == MobSpawnType.SPAWN_EGG)
            this.lostTargetTime = 0;
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pData, pTag);
    }

    public boolean isBuilding() {
        return this.entityData.get(DATA_BUILDING);
    }

    public void setBuilding(boolean pBuilding) {
        this.entityData.set(DATA_BUILDING, pBuilding);
    }

    public int getAge() {
        return this.entityData.get(DATA_AGE);
    }

    public boolean isMaxAge() {
        return this.getAge() >= MAX_AGE;
    }

    public void setAge(int pAge) {
        this.entityData.set(DATA_AGE, pAge);
    }

    public void increaseAge() {
        this.setAge(this.getAge() + 1);
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int pFlag) {
        this.entityData.set(DATA_FLAGS, pFlag);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(idle, spread);
    }

    protected int nextConvertUpNeeds() {
        return Integer.MAX_VALUE;
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return null;
    }

    public int getLevel() {
        return 3;
    }

    public int getExperienceReward() {
        return switch (this.getAge()) {
            case 0 -> 1;
            case 1 -> 5;
            case 2 -> 10;
            default -> 20;
        };
    }

    public int getSummonedLevel() {
        int age = this.getAge();
        if (age == 0)
            return 0;
        else
            return 1;
    }

    public int getSpawnCountByAge() {
        int age = this.getAge();
        if (age < 2)
            return 3;
        else if (age < 4)
            return 5;
        else
            return 7;
    }

    public float getHealthByAge() {
        int age = this.getAge();
        if (age == 0)
            return 10;
        else if (age == 1)
            return 60;
        else if (age == 2)
            return 90;
        else if (age == 3)
            return 110;
        else
            return 150;
    }

    public void sendNewBuilder() {
        PlumBuilder newBuilder = BlueOceansEntities.PLUM_BUILDER.get().create(level());
        Optional.ofNullable(newBuilder).ifPresent(builder -> {
            Vec3 pos = this.position();
            builder.moveTo(pos);
            builder.setTargetPos(pos.add((AbyssMath.randomMax(40, 20)), 0,
                    AbyssMath.randomMax(40, 20)));
            if (!this.level().addFreshEntity(builder))
                builder.discard();
        });
    }

    public void protectSelf(float pAmount) {
        if (this.getAge() == 0) {
            this.puffSound();
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            areaeffectcloud.setOwner(this);
            areaeffectcloud.setRadius(2.0F);
            areaeffectcloud.setRadiusOnUse(-0.5F);
            areaeffectcloud.setWaitTime(20);
            areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
            areaeffectcloud.addEffect(new MobEffectInstance(BlueOceansMobEffects.PLUM_INFECTION.get(),
                    200, 2));
            this.level().addFreshEntity(areaeffectcloud);
        } else if (this.getAge() == 1 && pAmount < 20.0F) {
            var list = RedPlumUtil.MAP.get(1);
            AbstractRedPlumMob mob = list.get(AbyssMath.random.nextInt(RedPlumUtil.BASE_PLUM_RANDOM_POOL))
                    .create(this.level());
            if (mob != null) {
                mob.moveTo(this.position().add(AbyssMath.random(5), 0, AbyssMath.random(5)));
                this.level().addFreshEntity(mob);
                NeoPlum.addParticleAroundPlum(mob);
            }
        }
        if (this.getAge() > 1 || (this.getAge() == 1 && pAmount >= 20.0F)) {
            var list = RedPlumUtil.MAP.get(2);
            AbstractRedPlumMob mob = list.get(AbyssMath.random.nextInt(2)).create(this.level());
            if (mob != null) {
                mob.moveTo(this.position().add(AbyssMath.random(5), 0, AbyssMath.random(5)));
                this.level().addFreshEntity(mob);
                NeoPlum.addParticleAroundPlum(mob);
            }
        }
    }

    public void spawnPlums() {
        List<EntityType<? extends AbstractRedPlumMob>> list = RedPlumUtil.MAP.get(
                this.getSummonedLevel());
        AbstractRedPlumMob plumMob = list.get(AbyssMath.random.nextInt(list.size())).create(level());
        if (plumMob != null) {
            plumMob.moveTo(position().add(AbyssMath.random(1.5), 0, AbyssMath.random(1.5)));
            if (level().addFreshEntity(plumMob))
                ParticleUtil.spawnAnim(plumMob, BlueOceansParticleTypes.RED_SPELL.get(), 8);
            else
                plumMob.discard();
        }
    }

    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource.is(DamageTypes.IN_WALL))
            return true;
        return super.isInvulnerableTo(pSource);
    }

    public boolean shouldAttackOtherMobs() {
        return false;
    }

    protected void addBehaviorGoal(int i, double speed, float range) {
    }

    protected void addMoveToPlumBehavior(int p, double pSpeed) {
    }

    public void push(double pX, double pY, double pZ) {
    }

    public void push(Entity pEntity) {
    }

    public boolean isControlledByLocalInstance() {
        return this.isEffectiveAi();
    }

    public static AttributeSupplier createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 150).add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.FOLLOW_RANGE, 64).add(Attributes.ARMOR, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .build();
    }

    public static void heal(PlumBuilder pMob) {
        heal(pMob, 1.0F);
    }

    public static void heal(PlumBuilder pMob, float amount) {
        if (pMob.getHealth() < pMob.getHealthByAge())
            pMob.heal(Math.min(pMob.getHealthByAge() - pMob.getHealth(), amount));
    }

    static {
        DATA_BUILDING = SynchedEntityData.defineId(PlumBuilder.class, EntityDataSerializers.BOOLEAN);
        DATA_AGE = SynchedEntityData.defineId(PlumBuilder.class, EntityDataSerializers.INT);
        DATA_FLAGS = SynchedEntityData.defineId(PlumBuilder.class, EntityDataSerializers.INT);
    }
}
