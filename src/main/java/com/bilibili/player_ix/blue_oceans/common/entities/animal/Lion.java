
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.github.player_ix.ix_api.api.mobs.IFlagMob;
import com.github.player_ix.ix_api.api.mobs.MobFoodData;
import com.github.player_ix.ix_api.api.mobs.MobUtils;
import com.github.player_ix.ix_api.api.mobs.ai.goal.MeleeGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.Level;
import org.nine_abyss.util.IXUtil;
import org.nine_abyss.util.IXUtilUser;
import org.nine_abyss.util.function.FunctionCollector;

import java.util.List;

public class Lion
extends BoAnimal
implements IFlagMob, IAnimatedMob {
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK;
    protected int jumpCooldown;
    public AnimationState attack = new AnimationState();
    public AnimationState jump = new AnimationState();
    public AnimationState ground = new AnimationState();
    public Lion(EntityType<? extends Lion> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
        this.entityData.define(DATA_ATTACK_TICK, 0);
    }

    public void aiStep() {
        super.aiStep();
        if (this.jumpCooldown > 0)
            --this.jumpCooldown;
        if (this.getTarget() != null) {
            this.doFlags(this.getTarget());
        }
        if (this.timeToJump()) {
            this.jumpToTarget();
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeGoal(this, 0.7D));
        this.targetSelector.addGoal(2, new LionTargetGoal(this));
    }

    public MobFoodData createFoodData() {
        return new MobFoodData(this, 30);
    }

    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof LivingEntity entity) {
            this.foodData.eat(entity);
        }
        return super.doHurtTarget(pEntity);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        this.foodData.addAdditionalSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.foodData.readAdditionalSaveData(pCompound);
    }

    private boolean timeToJump() {
        return this.jumpCooldown <= 0 && this.getTarget() != null;
    }

    private void jumpToTarget() {
        MobUtils.moveToLookAt(this, 3.0D);
        this.jumpCooldown = 80;
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public int getAniTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAniTick(int aniTick) {
        this.entityData.set(DATA_ATTACK_TICK, aniTick);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(attack, jump, ground);
    }

    private void doFlags(LivingEntity pTarget) {
        switch (this.getFlag()) {
            case 1: {
                increaseAniTick();
                if (this.aniTickEquals(12)) {
                    if (this.closerThan(pTarget, 2.3D)) {
                        this.doHurtTarget(pTarget);
                    }
                }
                if (this.aniTick(25)) {
                    this.resetState();
                    break;
                }
                break;
            }
            case 2: {
                increaseAniTick();
                if (this.aniTick(26)) {
                    this.resetState();
                }
                break;
            }
            case 3: {
                increaseAniTick();
                break;
            }
            default: {
                break;
            }
        }
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Lion.class, EntityDataSerializers.INT);
        DATA_ATTACK_TICK = SynchedEntityData.defineId(Lion.class, EntityDataSerializers.INT);
    }

    private static class LionTargetGoal extends NearestAttackableTargetGoal<LivingEntity>
    implements IXUtilUser {
        public LionTargetGoal(Lion pMob) {
            super(pMob, LivingEntity.class, 20, true, false,
                    FunctionCollector.alwaysTrue());
        }

        public boolean canUse() {
            Lion lion = this.convert();
            return lion.foodData.needsFood() && super.canUse();
        }

        public <T> T convert() {
            return IXUtil.c.convert(this.mob);
        }
    }
}
