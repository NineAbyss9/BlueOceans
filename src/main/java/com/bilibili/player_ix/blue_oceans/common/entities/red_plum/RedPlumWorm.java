
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class RedPlumWorm extends RedPlumMonster {
    public RedPlumWorm(EntityType<? extends RedPlumMonster > p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.xpReward = 1;
    }

    public RedPlumWorm(PlayMessages.SpawnEntity entity, Level world) {
        this(BlueOceansEntities.RED_PLUM_WORM.get(), world);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected SoundEvent getAmbientSound() {
        return BlueOceansSounds.RedPlumWormIdle.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return BlueOceansSounds.RedPlumWormHurt.get();
    }

    protected SoundEvent getDeathSound() {
        return BlueOceansSounds.RedPlumWormDie.get();
    }

    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(BlueOceansSounds.RedPlumWormStep.get());
    }

    protected void registerGoals() {
        super.registerGoals();
        this.addMeleeAttackGoal(2, 1, 3);
    }

    public boolean killedEntity(ServerLevel pLevel, LivingEntity pEntity) {
        if (this.getOwner() != null) {
            this.getOwner().heal(2);
        }
        if (this.getOwner() instanceof AbstractRedPlumMob mobs) {
            mobs.setKillsPlus();
        }
        return super.killedEntity(pLevel, pEntity);
    }

    public void die(DamageSource source) {
        this.dropAllDeathLoot(source);
        if (!level().isClientSide)
            this.serverLevel().sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(),
                    30, 0.5, 0.5, 0.5, 0.1);
        if (this.getOwner() != null && this.getOwner() instanceof AbstractRedPlumMob mobs) {
            mobs.heal(mobs.getHealAmount());
        }
        this.remove(RemovalReason.KILLED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = AbstractRedPlumMob.createLivingAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.MAX_HEALTH, 16)
                .add(Attributes.FOLLOW_RANGE, 40).add(Attributes.ATTACK_KNOCKBACK, 1);
        return builder;
    }
}
