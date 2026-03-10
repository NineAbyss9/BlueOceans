
package com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.Paramecium;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.WaterAnimal;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.List;

public class Shrimp
extends WaterAnimal
implements IAnimatedMob, IFlagMob {
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    protected static final EntityDataAccessor<Integer> DATA_ANIM_TICK;
    public AnimationState pose = new AnimationState();
    public AnimationState idle = new AnimationState();
    public AnimationState dig = new AnimationState();
    public Shrimp(EntityType<? extends Shrimp> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.random.setSeed(this.getId());
        this.moveControl = new Paramecium.ParameciumMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
        this.entityData.define(DATA_ANIM_TICK, 0);
    }

    public void aiStep() {
        super.aiStep();
        if (this.getFlag() == 0 && this.tickCount % 20 == 0 && Math.random() < 0.3 &&
                canDig())
            this.setFlag(1);
        this.doFlags();
    }

    protected void clientAiStep() {
        if (this.getFlag() == 0) this.idle.startIfStopped(tickCount);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.2d));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 0.8d, 40));
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey) && this.level().isClientSide) {
            switch (this.getFlag())
            {
                case 0: {
                    this.pose.startIfStopped(tickCount);
                    break;
                }
                case 1:{
                    this.stopAllAnimations();
                    this.dig.startIfStopped(tickCount);
                    break;
                }
                default: {
                    BlueOceans.LOGGER.warn("Can't handle flag in {}, call NineAbyss!", this.getClass());
                    this.setFlag(0);
                    break;
                }
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    public void doFlags() {
        if (this.getFlag() == 1) {
            increaseAniTick();
            if (this.aniTickEquals(15) || this.aniTickEquals(20) || this.aniTickEquals(25)) {
                if (this.level().isClientSide) {
                    for (int i = 0;i<5;i++)
                        ParticleUtil.addBlockParticle(level(), blockPosition().below(), getX(), getY(), getZ());
                } else {
                    if (this.random.nextFloat() < 0.05F) {
                        ItemStack stack = new ItemStack(Items.BONE_MEAL);
                        if (BoneMealItem.growWaterPlant(stack, level(), blockPosition(), Direction.UP))
                            level().levelEvent(1505, blockPosition(), 0);
                    }
                }
                this.playSound(level().getBlockState(this.blockPosition().below()).getSoundType().getHitSound());
            }
            if (this.aniTick(30)) {
                this.resetState();
            }
        }
    }

    public boolean canDig() {
        return !level().getBlockState(blockPosition().below()).isAir()
                && !(level().getBlockState(blockPosition().below()).getBlock() instanceof LiquidBlock)
                && !(level().getBlockState(blockPosition().below()).getBlock() instanceof BushBlock);
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int i) {
        this.entityData.set(DATA_FLAGS, i);
    }

    public int getAniTick() {
        return this.entityData.get(DATA_ANIM_TICK);
    }

    public void setAniTick(int aniTick) {
        this.entityData.set(DATA_ANIM_TICK, aniTick);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(dig);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01f, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9d));
            if (this.getTarget() == null)
                this.setDeltaMovement(this.getDeltaMovement().add(0.d, -0.005d, 0.d));
        } else
            super.travel(pTravelVector);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.ARMOR, 2).add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.MAX_HEALTH, 6).add(ForgeMod.SWIM_SPEED.get(), 10);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Shrimp.class, EntityDataSerializers.INT);
        DATA_ANIM_TICK = SynchedEntityData.defineId(Shrimp.class, EntityDataSerializers.INT);
    }
}
