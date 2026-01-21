
package com.bilibili.player_ix.blue_oceans.common.entities.animal.freshwater;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.WaterAnimal;
import com.github.player_ix.ix_api.api.mobs.IFlagMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import org.nine_abyss.util.Option;

import javax.annotation.Nullable;
import java.util.List;

public class RiverCrab
extends WaterAnimal
implements IAnimatedMob, IFlagMob {
    private static final EntityDataAccessor<Integer> DATA_FLAGS;
    public AnimationState idle = new AnimationState();
    public RiverCrab(EntityType<? extends RiverCrab> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
    }

    public void aiStep() {
        super.aiStep();
        if (this.getTask().equals(Task.DEFECATION) && this.random.nextInt(14) == 0) {
            CropBlock crop = this.cropBelow();
            Option.ofNullable(crop).ifPresent(block ->
                    block.growCrops(this.level(), blockPosition(), this.level().getBlockState(blockPosition())));
        }
    }

    @Nullable
    public CropBlock cropBelow() {
        return this.level().getBlockState(this.blockPosition()).getBlock() instanceof CropBlock block ? block : null;
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(idle);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(RiverCrab.class, EntityDataSerializers.INT);
    }
}
