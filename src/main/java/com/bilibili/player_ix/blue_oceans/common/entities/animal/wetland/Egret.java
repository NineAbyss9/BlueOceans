
package com.bilibili.player_ix.blue_oceans.common.entities.animal.wetland;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.farm.Earthworm;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean.Shrimp;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

//鹭
public class Egret
extends BoAnimal
implements IAnimatedMob, IFlagMob {
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    protected static final EntityDataAccessor<Integer> DATA_ANIM_TICK;
    private static final int FLAG_IDLE = 0;
    private static final int FLAG_PECK = 1;
    private static final int PECK_DURATION = 12;
    public AnimationState idle = new AnimationState();
    public AnimationState peck = new AnimationState();
    private int peckCooldown;

    public Egret(EntityType<? extends Egret> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, FLAG_IDLE);
        this.entityData.define(DATA_ANIM_TICK, 0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.3D));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1D, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Shrimp.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Earthworm.class, true));
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.tickPeckServer();
        }
    }

    protected void clientAiStep() {
        if (this.getFlag() == FLAG_IDLE) {
            this.idle.startIfStopped(this.tickCount);
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey) && this.level().isClientSide) {
            switch (this.getFlag()) {
                case FLAG_IDLE: {
                    this.stopAllAnimations();
                    this.idle.startIfStopped(this.tickCount);
                    break;
                }
                case FLAG_PECK: {
                    this.stopAllAnimations();
                    this.peck.startIfStopped(this.tickCount);
                    break;
                }
                default: {
                    BlueOceans.LOGGER.warn("Can't handle flag in {}, call NineAbyss!", this.getClass());
                    this.setFlag(FLAG_IDLE);
                    break;
                }
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    private void tickPeckServer() {
        if (this.getFlag() == FLAG_PECK) {
            this.increaseAniTick();
            if (this.aniTick(PECK_DURATION)) {
                this.resetState();
                this.peckCooldown = 60;
            }
            return;
        }
        if (this.peckCooldown > 0) {
            --this.peckCooldown;
            return;
        }
        if (this.onGround() && this.getTarget() == null && this.random.nextFloat() < 0.02F) {
            this.setAniTick(0);
            this.setFlag(FLAG_PECK);
        }
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public int getAniTick() {
        return this.entityData.get(DATA_ANIM_TICK);
    }

    public void setAniTick(int aniTick) {
        this.entityData.set(DATA_ANIM_TICK, aniTick);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(idle, peck);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Egret.class, EntityDataSerializers.INT);
        DATA_ANIM_TICK = SynchedEntityData.defineId(Egret.class, EntityDataSerializers.INT);
    }
}
