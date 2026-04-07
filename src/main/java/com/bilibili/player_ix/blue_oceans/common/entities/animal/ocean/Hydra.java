
package com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean;

import com.bilibili.player_ix.blue_oceans.api.mob.Cnidarians;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.WaterAnimal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class Hydra
extends WaterAnimal
implements Cnidarians {
    private static final int BUD_INTERVAL = 1200;
    public AnimationState idle = new AnimationState();

    public Hydra(EntityType<? extends Hydra> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        super.tick();
        if (this.isInWaterOrBubble()) {
            // Hydra in reality are mostly attached and drift very little.
            Vec3 movement = this.getDeltaMovement();
            this.setDeltaMovement(movement.x * 0.2D, movement.y * 0.6D, movement.z * 0.2D);
        }
        this.tickSting();
        if (!this.level().isClientSide)
        {
            this.regenerateInWater();
            this.tryBudding();
        }
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(tickCount);
    }

    public Vec3 getSize() {
        return new Vec3(0.3D, 0.6D, 0.3D);
    }

    public void sting(LivingEntity pEntity) {
        pEntity.hurt(this.damageSources().cactus(), 0.5F);
        pEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
        pEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0));
    }

    private void regenerateInWater() {
        if (!this.isInWaterOrBubble()) return;
        if (this.tickCount % 40 != 0) return;
        if (this.getHealth() >= this.getMaxHealth()) return;
        this.heal(0.5F);
    }

    private void tryBudding() {
        if (!this.isInWaterOrBubble()) return;
        if (this.getHealth() < this.getMaxHealth() * 0.8F) return;
        if (this.tickCount % BUD_INTERVAL != 0) return;
        if (this.random.nextInt(4) != 0) return;

        Hydra child = (Hydra)this.getType().create(serverLevel());
        if (child == null) return;

        child.moveTo(this.getX() + (this.random.nextDouble() - 0.5D) * 0.6D,
                this.getY(),
                this.getZ() + (this.random.nextDouble() - 0.5D) * 0.6D,
                this.getYRot(),
                this.getXRot());
        serverLevel().addFreshEntity(child);
    }

    public static AttributeSupplier createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.02D)
                .add(Attributes.ATTACK_DAMAGE, 0.5D)
                .add(ForgeMod.SWIM_SPEED.get(), 0.1D)
                .build();
    }
}
