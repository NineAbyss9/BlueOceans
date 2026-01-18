
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class RedPlumSpider extends RedPlumMonster {
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID;
    public RedPlumSpider(EntityType<? extends RedPlumSpider> type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.addMeleeAttackGoal(1, 1.2, 2F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.5F;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType
            pReason, SpawnGroupData pData, CompoundTag pTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pData, pTag);
        if (data == null) {
            data = new SpiderData();
            if (pLevel.getDifficulty() == Difficulty.HARD && pLevel.getRandom().nextFloat() < 0.1F
                    * pDifficulty.getSpecialMultiplier()) {
                ((SpiderData)data).setRandomEffect(pLevel.getRandom());
                MobEffect effect = ((SpiderData)data).effect;
                if (effect != null) {
                    this.addEffect(new MobEffectInstance(effect, -1));
                }
            }
        }
        return data;
    }

    protected PathNavigation createNavigation(Level p_33802_) {
        return new WallClimberNavigation(this, p_33802_);
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean p_33820_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_33820_) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 &= -2;
        }
        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33814_) {
        return SoundEvents.SPIDER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    protected void playStepSound(BlockPos p_33804_, BlockState p_33805_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    protected float getStandingEyeHeight(Pose p_33799_, EntityDimensions p_33800_) {
        return 0.65F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MAX_HEALTH, 16)
                .add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.ATTACK_KNOCKBACK, 0.3)
                .add(Attributes.FOLLOW_RANGE, 42);
    }

    static {
        DATA_FLAGS_ID = SynchedEntityData.defineId(RedPlumSpider.class, EntityDataSerializers.BYTE);
    }

    public static class SpiderData implements SpawnGroupData {
        @Nullable
        public MobEffect effect;

        public SpiderData() {
        }

        public void setRandomEffect(RandomSource p_219119_) {
            int i = p_219119_.nextInt(5);
            if (i <= 1) {
                this.effect = MobEffects.MOVEMENT_SPEED;
            } else if (i == 2) {
                this.effect = MobEffects.DAMAGE_BOOST;
            } else if (i == 3) {
                this.effect = MobEffects.REGENERATION;
            } else if (i == 4) {
                this.effect = MobEffects.INVISIBILITY;
            }
        }
    }
}
