
package com.bilibili.player_ix.blue_oceans.common.entities.traffic;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Car
extends AbstractTrafficUtil {
    public Car(EntityType<? extends Car> type, Level level) {
        super(type, level);
    }

    @Nullable
    public SoundEvent getStepSound() {
        return super.getStepSound();
    }

    protected void playStepSound(BlockPos pPos, BlockState pState) {
        super.playStepSound(pPos, pState);
    }

    public static AttributeSupplier createAttributes() {
        return createPathAttributes().add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.MAX_HEALTH, 60).add(Attributes.ARMOR, 10).build();
    }
}
