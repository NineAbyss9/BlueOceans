
package com.bilibili.player_ix.blue_oceans.common.entities.traffic;

import com.github.player_ix.ix_api.api.mobs.ApiPathfinderMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;

public abstract class AbstractTrafficUtil
extends ApiPathfinderMob {
    protected static final AttributeModifier SPEEDY_SPEED_MODIFIER;
    protected static final EntityDataAccessor<Boolean> DATA_SPEEDY;
    private static final EntityDataAccessor<Float> DATA_ARMOR;
    protected AbstractTrafficUtil(EntityType<? extends AbstractTrafficUtil> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SPEEDY, false);
        this.entityData.define(DATA_ARMOR, 0.0F);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isServerSide() && !this.isVehicle()) {
            this.dropItems();
            this.discard();
        }
        return false;
    }

    public void hurt(float pAmount) {
        this.setHealth(this.getHealth() - pAmount);
    }

    protected void dropItems() {
    }

    public float getArmor() {
        return this.entityData.get(DATA_ARMOR);
    }

    public void setArmor(float pArmor) {
        this.entityData.set(DATA_ARMOR, pArmor);
    }

    /**0 -> Car
     * 1 -> Bike
     * @return the type of the traffic util*/
    public int getTrafficType() {
        return 1;
    }

    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.isVehicle() && !pPlayer.isSecondaryUseActive()) {
            if (!this.level().isClientSide) {
                pPlayer.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        return entity instanceof Player ? (Player)entity : null;
    }

    protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
        this.setRot(pPlayer.getYRot(), pPlayer.getXRot() * 0.5F);
        this.yBodyRotO = this.yBodyRot;
        this.yHeadRotO = this.yHeadRot = pPlayer.yHeadRot;
    }

    public void travel(Vec3 pTravelVector) {
        Entity entity = this.getControllingPassenger();
        if (this.isVehicle()) {
            double d0;
            double d1;
            float f1;
            if (entity instanceof LivingEntity passenger) {
                this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float forward = passenger.zza;
                float strafe = passenger.xxa;
                super.travel(new Vec3(strafe, 0.0, forward));
            }
            if ((f1 = (float)Math.sqrt((d1 = this.getX() - this.xo) * d1 + (d0 = this.getZ() - this.zo) * d0) * 4.0f) > 1.0f) {
                f1 = 1.0f;
            }
            this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4f);
            this.walkAnimation.speed(this.walkAnimation.speed() + this.walkAnimation.speed());
            this.calculateEntityAnimation(true);
            return;
        }
        super.travel(pTravelVector);
    }

    public boolean isSpeedy() {
        return this.entityData.get(DATA_SPEEDY);
    }

    public void setSpeedy(boolean pSpeedy) {
        this.addOrRemoveAttributeModifier(Attributes.MOVEMENT_SPEED, SPEEDY_SPEED_MODIFIER, pSpeedy);
        this.entityData.set(DATA_SPEEDY, pSpeedy);
    }

    static {
        SPEEDY_SPEED_MODIFIER = new AttributeModifier("TrafficUtilSpeed",
                1.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        DATA_SPEEDY = SynchedEntityData.defineId(AbstractTrafficUtil.class, EntityDataSerializers.BOOLEAN);
        DATA_ARMOR = SynchedEntityData.defineId(AbstractTrafficUtil.class, EntityDataSerializers.FLOAT);
    }
}
