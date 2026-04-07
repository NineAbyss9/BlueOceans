
package com.bilibili.player_ix.blue_oceans.common.entities.animal.flying;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.util.Option;

import javax.annotation.Nullable;

public class Bird
extends AbstractFlyingAnimal {
    protected static final EntityDataAccessor<Integer> DATA_TYPE;
    public float flap;
    public float oFlap;
    public float flapSpeed;
    public float oFlapSpeed;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;
    public Bird(EntityType<? extends Bird> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE, 0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new WanderGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return pDimensions.height * 0.6F;
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (!this.onGround() && !this.isPassenger() ? 4F : -1F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }
        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }
        this.flap += this.flapping * 2.0F;
    }

    protected Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public int getBirdType() {
        return this.entityData.get(DATA_TYPE);
    }

    public void setBirdType(int pType) {
        this.entityData.set(DATA_TYPE, Mth.clamp(pType, 0, 3));
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData,
                                        @Nullable CompoundTag pDataTag) {
        this.setBirdType(pLevel.getRandom().nextInt(4));
        Option<SpawnGroupData> option = Option.ofNullable(pSpawnData);
        SpawnGroupData data = option.orElse(new AgeableMobGroupData(false));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, data, pDataTag);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FLYING_SPEED, 0.45D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D);
    }

    static
    {
        DATA_TYPE = SynchedEntityData.defineId(Bird.class, EntityDataSerializers.INT);
    }

    public static class WanderGoal extends WaterAvoidingRandomFlyingGoal
    {
        public WanderGoal(PathfinderMob pMob)
        {
            super(pMob, 1.0D);
        }

        @Nullable
        public Vec3 getPosition()
        {
            Vec3 vec3 = null;
            if (this.mob.isInWater())
            {
                vec3 = LandRandomPos.getPos(this.mob, 15, 15);
            }
            if (this.mob.getRandom().nextFloat() >= this.probability)
            {
                vec3 = this.getTreePos();
            }
            return vec3 == null ? super.getPosition() : vec3;
        }

        @Nullable
        public Vec3 getTreePos()
        {
            BlockPos blockpos = this.mob.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
            for (BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0D), Mth
                    .floor(this.mob.getY() - 6.0D), Mth.floor(this.mob.getZ() - 3.0D), Mth.floor(
                            this.mob.getX() + 3.0D), Mth.floor(this.mob.getY() + 6.0D),
                    Mth.floor(this.mob.getZ() + 3.0D)))
            {
                if (!blockpos.equals(blockpos1))
                {
                    BlockState blockstate = this.mob.level().getBlockState(blockpos$mutableblockpos1
                            .setWithOffset(blockpos1, Direction.DOWN));
                    boolean flag = blockstate.getBlock() instanceof LeavesBlock || blockstate.is(BlockTags.LOGS);
                    if (flag && this.mob.level().isEmptyBlock(blockpos1) && this.mob.level().isEmptyBlock(
                            blockpos$mutableblockpos.setWithOffset(blockpos1, Direction.UP)))
                    {
                        return Vec3.atBottomCenterOf(blockpos1);
                    }
                }
            }
            return null;
        }
    }
}
