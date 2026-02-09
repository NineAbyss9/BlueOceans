
package com.bilibili.player_ix.blue_oceans.common.entities.traffic;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Car
extends AbstractTrafficUtil {
    public Car(EntityType<? extends Car> type, Level level) {
        super(type, level);
    }

    public SoundEvent getStepSound() {
        return SoundEvents.AMETHYST_BLOCK_STEP;
    }

    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount % 5 == 0)
            this.playSound(this.getStepSound(), this.getSoundVolume() * 0.5F, this.getVoicePitch());
    }

    protected void dropItems() {
        this.spawnAtLocation(ItemStack.EMPTY);
    }

    public static AttributeSupplier createAttributes() {
        return createPathAttributes().add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.MAX_HEALTH, 60).add(Attributes.ARMOR, 10).build();
    }
}
