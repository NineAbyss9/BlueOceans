
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAcceptTask;
import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.github.player_ix.ix_api.api.mobs.FoodDataUser;
import com.github.player_ix.ix_api.api.mobs.MobFoodData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;

public class BoAnimal
extends Animal
implements FoodDataUser, IAcceptTask {
    protected static final EntityDataAccessor<Integer> DATA_TASK;
    protected final MobFoodData foodData;
    protected BoAnimal(EntityType<? extends BoAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.foodData = this.createFoodData();
    }

    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.clientAiStep();
        }
    }

    protected void clientAiStep() {
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TASK, 0);
    }

    public MobFoodData foodData() {
        return this.foodData;
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

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("FoodData", this.foodData.integration());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.foodData.readIntegration(pCompound);
    }

    static {
        DATA_TASK = SynchedEntityData.defineId(BoAnimal.class, EntityDataSerializers.INT);
    }
}
