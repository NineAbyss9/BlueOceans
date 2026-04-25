
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.CompletelyPerverseState;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.flying.AbstractFlyingAnimal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.List;

/** Silkworm (larva and pupa) and silk moth (adult). */
public class Silkworm
extends AbstractFlyingAnimal
implements IAnimatedMob, CompletelyPerverseState.Interface {
    private static final EntityDataAccessor<Integer> DATA_STATE;
    private static final Ingredient LEAF_FOODS = Ingredient.of(Items.OAK_LEAVES, Items.BIRCH_LEAVES,
            Items.JUNGLE_LEAVES, Items.ACACIA_LEAVES, Items.DARK_OAK_LEAVES, Items.MANGROVE_LEAVES,
            Items.CHERRY_LEAVES, Items.AZALEA, Items.FLOWERING_AZALEA);
    /** Ticks as larva before pupating. */
    public static final int LARVA_DURATION = 6000;
    /** Ticks as pupa before eclosion. */
    public static final int PUPA_DURATION = 3000;
    /** Larva silk strand drop interval check (tick modulus). */
    public static final int SILK_INTERVAL = 200;
    public AnimationState idle = new AnimationState();
    private int stageTick;

    public Silkworm(EntityType<? extends Silkworm> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 9, true);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STATE, CompletelyPerverseState.LARVA.id);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.05D));
        this.goalSelector.addGoal(2, new LarvaStrollGoal(this));
        this.goalSelector.addGoal(2, new PupaStillGoal(this));
        this.goalSelector.addGoal(3, new MothFlyGoal(this));
        this.goalSelector.addGoal(4, new LarvaTemptGoal(this));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.tickLifeCycle();
            this.tickSilk();
        }
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(this.tickCount);
    }

    private void tickLifeCycle() {
        CompletelyPerverseState state = this.getPerState();
        if (state.isLarva()) {
            ++this.stageTick;
            if (this.stageTick >= LARVA_DURATION) {
                this.setPerState(CompletelyPerverseState.PUPA);
                this.stageTick = 0;
            }
        } else if (state.isPupa()) {
            ++this.stageTick;
            if (this.stageTick >= PUPA_DURATION) {
                this.setPerState(CompletelyPerverseState.ADULT);
                this.stageTick = 0;
            }
        }
    }

    private void tickSilk() {
        if (!this.getPerState().isLarva()) {
            return;
        }
        if (this.tickCount % SILK_INTERVAL == 0 && this.random.nextFloat() < 0.18F) {
            this.produceSilk();
        }
    }

    private void produceSilk() {
        int n = 1 + this.random.nextInt(2);
        this.spawnAtLocation(new ItemStack(Items.STRING, n));
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData,
                                        @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.NATURAL && pLevel.getRandom().nextFloat() < 0.72F) {
            this.entityData.set(DATA_STATE, CompletelyPerverseState.ADULT.id);
        }
        return data;
    }

    public boolean isFlying() {
        return this.getPerState().isAdult() && super.isFlying();
    }

    public void onBaby(boolean pBaby) {
        super.onBaby(pBaby);
        if (!this.level().isClientSide) {
            this.setPerState(pBaby ? CompletelyPerverseState.LARVA : CompletelyPerverseState.ADULT);
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_STATE.equals(pKey)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(pKey);
    }

    public CompletelyPerverseState getPerState() {
        return CompletelyPerverseState.of(this.entityData.get(DATA_STATE));
    }

    public void setPerState(CompletelyPerverseState pState) {
        int next = pState.id;
        if (this.entityData.get(DATA_STATE) == next) return;
        this.entityData.set(DATA_STATE, next);
        this.refreshDimensions();
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(this.idle);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("StageTick", this.stageTick);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.stageTick = pCompound.getInt("StageTick");
    }

    static
    {
        DATA_STATE = SynchedEntityData.defineId(Silkworm.class, EntityDataSerializers.INT);
    }

    public static class LarvaStrollGoal extends RandomStrollGoal
    {
        private final Silkworm worm;

        public LarvaStrollGoal(Silkworm pWorm)
        {
            super(pWorm, 0.32D);
            this.worm = pWorm;
        }

        public boolean canUse()
        {
            return this.worm.getPerState().isLarva() && super.canUse();
        }

        public boolean canContinueToUse()
        {
            return this.worm.getPerState().isLarva() && super.canContinueToUse();
        }
    }

    public static class PupaStillGoal extends Goal
    {
        private final Silkworm worm;

        public PupaStillGoal(Silkworm pWorm)
        {
            this.worm = pWorm;
        }

        public boolean canUse()
        {
            return this.worm.getPerState().isPupa();
        }

        public boolean canContinueToUse()
        {
            return this.worm.getPerState().isPupa();
        }

        public void tick()
        {
            this.worm.getNavigation().stop();
        }
    }

    public static class MothFlyGoal extends WaterAvoidingRandomFlyingGoal
    {
        private final Silkworm worm;

        public MothFlyGoal(Silkworm pWorm)
        {
            super(pWorm, 0.85D);
            this.worm = pWorm;
        }

        public boolean canUse()
        {
            return this.worm.getPerState().isAdult() && super.canUse();
        }

        public boolean canContinueToUse()
        {
            return this.worm.getPerState().isAdult() && super.canContinueToUse();
        }
    }

    public static class LarvaTemptGoal extends TemptGoal
    {
        private final Silkworm worm;

        public LarvaTemptGoal(Silkworm pWorm)
        {
            super(pWorm, 0.55D, LEAF_FOODS, false);
            this.worm = pWorm;
        }

        public boolean canUse()
        {
            return this.worm.getPerState().isLarva() && super.canUse();
        }

        public boolean canContinueToUse()
        {
            return this.worm.getPerState().isLarva() && super.canContinueToUse();
        }
    }
}
