
package com.bilibili.player_ix.blue_oceans.common.entities.animal.flying;

import com.bilibili.player_ix.blue_oceans.api.mob.CompletelyPerverseState;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.ItemTags;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.List;

/** Caterpillar, chrysalis, and nectar-feeding adult (full metamorphosis). */
public class Butterfly
extends AbstractFlyingAnimal
implements CompletelyPerverseState.Interface, IAnimatedMob {
    private static final EntityDataAccessor<Integer> DATA_STATE;
    private static final Ingredient HOST_LEAVES = Ingredient.of(Items.OAK_LEAVES, Items.BIRCH_LEAVES,
            Items.JUNGLE_LEAVES, Items.ACACIA_LEAVES, Items.DARK_OAK_LEAVES, Items.MANGROVE_LEAVES,
            Items.CHERRY_LEAVES, Items.AZALEA, Items.FLOWERING_AZALEA);
    private static final Ingredient NECTAR = Ingredient.of(ItemTags.SMALL_FLOWERS);
    public static final int LARVA_DURATION = 4800;
    public static final int PUPA_DURATION = 2400;
    public AnimationState idle = new AnimationState();
    private int stageTick;

    public Butterfly(EntityType<? extends Butterfly> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STATE, CompletelyPerverseState.LARVA.id);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new CaterpillarStrollGoal(this));
        this.goalSelector.addGoal(2, new ChrysalisStillGoal(this));
        this.goalSelector.addGoal(3, new AdultFlutterGoal(this));
        this.goalSelector.addGoal(4, new CaterpillarFeedGoal(this));
        this.goalSelector.addGoal(5, new AdultNectarGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.tickMetamorphosis();
        }
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(this.tickCount);
    }

    private void tickMetamorphosis() {
        CompletelyPerverseState s = this.getPerState();
        if (s.isLarva()) {
            ++this.stageTick;
            if (this.stageTick >= LARVA_DURATION) {
                this.setPerState(CompletelyPerverseState.PUPA);
                this.stageTick = 0;
            }
        } else if (s.isPupa()) {
            ++this.stageTick;
            if (this.stageTick >= PUPA_DURATION) {
                this.setPerState(CompletelyPerverseState.ADULT);
                this.stageTick = 0;
            }
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData,
                                        @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.NATURAL && pLevel.getRandom().nextFloat() < 0.78F) {
            this.entityData.set(DATA_STATE, CompletelyPerverseState.ADULT.id);
        }
        return data;
    }

    public boolean isFlying() {
        return this.getPerState().isAdult() && super.isFlying();
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
        if (this.level().isClientSide) {
            return;
        }
        int next = pState.id;
        if (this.entityData.get(DATA_STATE) != next) {
            this.entityData.set(DATA_STATE, next);
            this.refreshDimensions();
        }
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(this.idle);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ButterflyStageTick", this.stageTick);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.stageTick = pCompound.getInt("ButterflyStageTick");
    }

    static {
        DATA_STATE = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.INT);
    }

    public static class CaterpillarStrollGoal extends RandomStrollGoal {
        private final Butterfly butterfly;

        public CaterpillarStrollGoal(Butterfly b) {
            super(b, 0.28D);
            this.butterfly = b;
        }

        public boolean canUse() {
            return this.butterfly.getPerState().isLarva() && super.canUse();
        }

        public boolean canContinueToUse() {
            return this.butterfly.getPerState().isLarva() && super.canContinueToUse();
        }
    }

    public static class ChrysalisStillGoal extends Goal {
        private final Butterfly butterfly;

        public ChrysalisStillGoal(Butterfly b) {
            this.butterfly = b;
        }

        public boolean canUse() {
            return this.butterfly.getPerState().isPupa();
        }

        public boolean canContinueToUse() {
            return this.butterfly.getPerState().isPupa();
        }

        public void tick() {
            this.butterfly.getNavigation().stop();
        }
    }

    public static class AdultFlutterGoal extends WaterAvoidingRandomFlyingGoal {
        private final Butterfly butterfly;

        public AdultFlutterGoal(Butterfly b) {
            super(b, 0.55D);
            this.butterfly = b;
        }

        public boolean canUse() {
            return this.butterfly.getPerState().isAdult() && super.canUse();
        }

        public boolean canContinueToUse() {
            return this.butterfly.getPerState().isAdult() && super.canContinueToUse();
        }
    }

    public static class CaterpillarFeedGoal extends TemptGoal {
        private final Butterfly butterfly;

        public CaterpillarFeedGoal(Butterfly b) {
            super(b, 0.5D, HOST_LEAVES, false);
            this.butterfly = b;
        }

        public boolean canUse() {
            return this.butterfly.getPerState().isLarva() && super.canUse();
        }

        public boolean canContinueToUse() {
            return this.butterfly.getPerState().isLarva() && super.canContinueToUse();
        }
    }

    public static class AdultNectarGoal extends TemptGoal {
        private final Butterfly butterfly;

        public AdultNectarGoal(Butterfly b) {
            super(b, 0.65D, NECTAR, false);
            this.butterfly = b;
        }

        public boolean canUse() {
            return this.butterfly.getPerState().isAdult() && super.canUse();
        }

        public boolean canContinueToUse() {
            return this.butterfly.getPerState().isAdult() && super.canContinueToUse();
        }
    }
}
