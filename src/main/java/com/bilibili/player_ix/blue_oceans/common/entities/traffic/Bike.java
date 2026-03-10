
package com.bilibili.player_ix.blue_oceans.common.entities.traffic;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Bike
extends AbstractTransport {
    public Bike(EntityType<? extends Bike> type, Level level) {
        super(type, level);
    }

    public Fallsounds getFallSounds() {
        return super.getFallSounds()
                //new Fallsounds(SoundEvents.GENERIC_SMALL_FALL, SoundEvents.GENERIC_BIG_FALL)
        ;
    }

    public SoundEvent getStepSound() {
        return BlueOceansSounds.BIKE_RUN.get();
    }

    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount % 3 == 0)
            this.playSound(this.getStepSound(), this.getSoundVolume() * 0.5F, this.getVoicePitch());
    }

    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.3D;
    }

    protected void dropItems() {
        this.spawnAtLocation(BlueOceansItems.BIKE_EGG.get());
    }

    public int getTrafficType() {
        return 0;
    }

    public static AttributeSupplier createAttributes() {
        return createPathAttributes().add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.MAX_HEALTH, 20).add(Attributes.ARMOR, 2)
                .add(Attributes.FOLLOW_RANGE, 16).build();
    }
}
