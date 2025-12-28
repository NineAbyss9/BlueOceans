
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class RedPlumCow
extends RedPlumMonster {
    public RedPlumCow(EntityType<? extends RedPlumCow> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.setMaxUpStep(1f);
        this.xpReward = 2;
        this.setNoAi(false);
    }

    @SuppressWarnings("unused")
    public RedPlumCow(PlayMessages.SpawnEntity packet, Level level) {
        this(BlueOceansEntities.RED_PLUMS_COW.get(), level);
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.COW_HURT;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0,
                false));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public float getVoicePitch() {
        return 0.25f;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractRedPlumMob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_KNOCKBACK, 1).add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.FOLLOW_RANGE, 40).add(Attributes.ARMOR, 2);
    }
}
