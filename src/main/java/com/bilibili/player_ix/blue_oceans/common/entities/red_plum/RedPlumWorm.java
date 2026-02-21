
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import com.github.NineAbyss9.ix_api.util.Vec9;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.math.AbyssMath;

public class RedPlumWorm extends RedPlumMonster {
    private int hideTick;
    private BlockPos hidePos = BlockPos.ZERO;
    private int tryCount;
    public RedPlumWorm(EntityType<? extends RedPlumWorm > p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.xpReward = 2;
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (this.getPlumFlag() == 99) {
                this.noPhysics = true;
                this.setNoGravity(true);
                this.setDeltaMovement(0, -0.01, 0);
                ++hideTick;
                if (hideTick > 200) {
                    this.hideTick = 0;
                    this.level().destroyBlock(this.blockPosition(), false);
                    this.level().setBlockAndUpdate(this.blockPosition(), BlueOceansBlocks.RED_PLUM_CATALYST.get()
                            .defaultBlockState());
                    this.discard();
                }
            } else {
                if (this.tickCount % 40 == 0 && this.navigation.isDone()) {
                    this.findHidePlace();
                    if (this.navigation.moveTo(this.hidePos.getX() + 0.5, this.hidePos.getY(), this.hidePos.getZ() + 0.5, 1))
                        tryCount = 0;
                    else
                        tryCount++;
                }
                if (this.tryCount > 2) {
                    this.setPlumFlag(99);
                    return;
                }
                Vec3 vec3 = Vec9.of(this.hidePos);
                if (Math.abs(this.x() - vec3.x) < 1 && Math.abs(this.z() - vec3.z) < 1) {
                    this.setPlumFlag(99);
                }
            }
        }
        if (this.level().isClientSide && this.getPlumFlag() == 99) {
            ParticleUtil.addBlockParticle(this.level(), this.blockPosition().below());
        }
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
        this.addMeleeAttackGoal(2, 1);
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

    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource.is(DamageTypes.IN_WALL))
            return true;
        return super.isInvulnerableTo(pSource);
    }

    public void findHidePlace() {
        for (int i = 0;i < 9;i++) {
            BlockPos pos = this.blockPosition().offset(AbyssMath.random(10), 0, AbyssMath.random(10));
            if (this.level().getBlockState(pos.below().below()).is(BoTags.RED_PLUM_BLOCKS))
                continue;
            this.hidePos = pos;
            break;
        }
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

    public boolean isImmobile() {
        return this.getPlumFlag() == 99;
    }

    public boolean shouldAttackOtherMobs() {
        return this.getPlumFlag() != 99 && super.shouldAttackOtherMobs();
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = AbstractRedPlumMob.createPathAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.MAX_HEALTH, 16)
                .add(Attributes.FOLLOW_RANGE, 40).add(Attributes.ATTACK_KNOCKBACK, 1);
        return builder;
    }
}
