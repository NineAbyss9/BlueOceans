
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAcceptTask;
import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.bilibili.player_ix.blue_oceans.common.blocks.be.CorpseEntity;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.github.NineAbyss9.ix_api.api.mobs.FoodDataUser;
import com.github.NineAbyss9.ix_api.api.mobs.MobFoodData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;

public class BoAnimal
extends Animal
implements FoodDataUser, IAcceptTask {
    protected static final EntityDataAccessor<Boolean> DATA_BABY;
    protected static final EntityDataAccessor<Integer> DATA_TASK;
    protected final MobFoodData foodData;
    protected BoAnimal(EntityType<? extends BoAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.foodData = this.createFoodData();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BABY, Boolean.FALSE);
        this.entityData.define(DATA_TASK, 0);
    }

    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.clientAiStep();
        }
    }

    protected void clientAiStep()
    {
    }

    public MobFoodData foodData() {
        return this.foodData;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_BABY.equals(pKey)) {
            this.onBaby(this.isBaby());
        }
        super.onSyncedDataUpdated(pKey);
    }

    public void onBaby(boolean pBaby) {
        this.refreshDimensions();
    }

    public ItemStack eat(Level pLevel, ItemStack pFood) {
        this.foodData.eat(pFood.getItem(), pFood, this);
        return super.eat(pLevel, pFood);
    }

    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return (AgeableMob)this.getType().create(pLevel);
    }

    public Task getTask() {
        return Task.fromId(this.entityData.get(DATA_TASK));
    }

    public void setTask(int pTask) {
        this.entityData.set(DATA_TASK, pTask);
    }

    public void spawnCorpse()
    {
        this.level().setBlock(this.blockPosition(), BlueOceansBlocks.CORPSE.get().defaultBlockState(), 0);
        if (this.level().getBlockEntity(this.blockPosition()) instanceof CorpseEntity entity) {
            entity.setEntity(this.getType());
        }
    }

    public void die(DamageSource pDamageSource)
    {
        super.die(pDamageSource);
        this.spawnCorpse();
    }

    public void spawnAnim()
    {
        if (this.level().isClientSide) {
            for(int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                double d3 = 10.0D;
                this.level().addParticle(ParticleTypes.POOF, this.getX(1.0D) - d0 * d3, this.getRandomY() - d1 * d3,
                        this.getRandomZ(1.0D) - d2 * d3, d0, d1, d2);
            }
        } else {
            this.level().broadcastEntityEvent(this, (byte)20);
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("FoodData", this.foodData.integration());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.foodData.readIntegration(pCompound);
    }

    static {
        DATA_BABY = SynchedEntityData.defineId(BoAnimal.class, EntityDataSerializers.BOOLEAN);
        DATA_TASK = SynchedEntityData.defineId(BoAnimal.class, EntityDataSerializers.INT);
    }
}
