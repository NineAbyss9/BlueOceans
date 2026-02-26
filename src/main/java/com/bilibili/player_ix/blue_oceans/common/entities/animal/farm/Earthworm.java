
package com.bilibili.player_ix.blue_oceans.common.entities.animal.farm;

import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.BoAnimal;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.NineAbyss9.math.MathSupport;

public class Earthworm
extends BoAnimal
implements IFlagMob {
    private static final EntityDataAccessor<Integer> DATA_ANIM_TICK;
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    //public AnimationState idle = new AnimationState();
    //public AnimationState dig = new AnimationState();
    //public AnimationState wake = new AnimationState();
    public Earthworm(EntityType<? extends Earthworm> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIM_TICK, 0);
        this.entityData.define(DATA_FLAGS, 0);
    }

    public void aiStep() {
        super.aiStep();
        if (this.isDigging() || this.hasTask(Task.WAKE)) {
            increaseAniTick();
        }
    }

    protected void clientAiStep() {
        /*if (this.isIdle()) {
            this.idle.startIfStopped(tickCount);
        } else*/
        if (this.isDigging()) {
            ParticleUtil.addBlockParticle(level(), blockPosition().below(), getX(), getY(), getZ());
        }
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.isHiding() && this.level().random.nextFloat() < 0.07F) {
            BlockState state = this.level().getBlockState(blockPosition().above());
            Block block = state.getBlock();
            if (block instanceof CropBlock crop && !crop.isMaxAge(state)) {
                crop.performBonemeal((ServerLevel)level(), level().random, blockPosition(), state);
            }
        }
    }

    /*public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey)) {
            int i = this.getFlag();
            if (i == 1) {
                this.stopAllAnimations();
                this.dig.startIfStopped(tickCount);
            } else if (i == 3) {
                this.stopAllAnimations();
                this.wake.startIfStopped(tickCount);
            }
        }
        super.onSyncedDataUpdated(pKey);
    }*/

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EarthwormDoTasksGoal());
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
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

    public boolean isDigging() {
        return this.getFlag() == 1;
    }

    public boolean isHiding() {
        return this.getFlag() == 2;
    }

    public void resetState() {
        this.resetTask();
        IFlagMob.super.resetState();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.ATTACK_DAMAGE, 2).add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MAX_HEALTH, 4).add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    static {
        DATA_ANIM_TICK = SynchedEntityData.defineId(Earthworm.class, EntityDataSerializers.INT);
        DATA_FLAGS = SynchedEntityData.defineId(Earthworm.class, EntityDataSerializers.INT);
    }

    protected class EarthwormDoTasksGoal extends Goal {
        public EarthwormDoTasksGoal() {
        }

        public boolean canUse() {
            return true;
        }

        public void tick() {
            if (Earthworm.this.isIdle() && MathSupport.random.nextFloat() < 0.05F) {
                Earthworm.this.setTask(Task.DIG);
                Earthworm.this.setFlag(1);
            } else if (Earthworm.this.hasTask(Task.DIG) && Earthworm.this.aniTick(40)) {
                Earthworm.this.setTask(Task.HIDE);
                Earthworm.this.setFlag(2);
                Earthworm.this.setAniTick(0);
            } else if (Earthworm.this.hasTask(Task.HIDE) && Earthworm.this.tickCount % 20 == 0 &&
                    MathSupport.random.nextFloat() < 0.125F) {
                Earthworm.this.setTask(Task.WAKE);
                Earthworm.this.setFlag(3);
            } else if (Earthworm.this.hasTask(Task.WAKE) && Earthworm.this.aniTick(20)) {
                Earthworm.this.resetState();
            }
        }
    }
}
