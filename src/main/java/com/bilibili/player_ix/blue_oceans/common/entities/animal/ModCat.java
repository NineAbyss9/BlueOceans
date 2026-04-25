
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.api.mob.ISleepMob;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.SleepGoal;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

/** Domestic cat: crepuscular rest, fish-motivated, stalks small prey. */
public class ModCat
extends BoAnimal
implements ISleepMob, IFlagMob, IAnimatedMob {
    private static final Ingredient TEMPT_FISH = Ingredient.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH);
    protected static final EntityDataAccessor<Integer> DATA_ANIM_TICK;
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    private int anInt = 0;
    public AnimationState idle = new AnimationState();
    public AnimationState sleep = new AnimationState();
    public AnimationState attack = new AnimationState();
    public AnimationState stalk = new AnimationState();
    public ModCat(EntityType<? extends BoAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIM_TICK, 0);
        this.entityData.define(DATA_FLAGS, 0);
    }

    protected void clientAiStep() {
        if (this.getFlag() == 0) {
            this.idle.startIfStopped(this.tickCount);
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey) && this.level().isClientSide) {
            switch (this.getFlag()) {
                case 1 -> this.startAfterStop(this.sleep);
                case 2 -> this.startAfterStop(this.attack);
                case 3 -> this.startAfterStop(this.stalk);
                default -> {
                }
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 0.9D, TEMPT_FISH, false));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.05D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.65D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new SleepGoal<>(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true,
                this::isStalkablePrey));
    }

    private boolean isStalkablePrey(LivingEntity e) {
        if (!e.isAlive() || !this.foodData.needsFood()) {
            return false;
        }
        return e instanceof Mouse || e instanceof Rabbit || e instanceof Chicken;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.CAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    public boolean isFood(ItemStack pStack) {
        return TEMPT_FISH.test(pStack);
    }

    /** Cats nap a lot by day when light is strong (sunbathing / dozing). */
    public boolean canSleep() {
        if (this.level().isNight()) {
            return true;
        }
        return this.level().getMaxLocalRawBrightness(this.blockPosition()) >= 12
                && this.random.nextFloat() < 0.004F;
    }

    public boolean isSleeping() {
        return super.isSleeping();
    }

    public void setSleeping(boolean sleeping) {
        this.setFlag(sleeping ? 1 : 0);
    }

    public void meow() {
        this.playSound(this.getAmbientSound());
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(this.idle, this.sleep);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    static {
        DATA_ANIM_TICK = SynchedEntityData.defineId(ModCat.class, EntityDataSerializers.INT);
        DATA_FLAGS = SynchedEntityData.defineId(ModCat.class, EntityDataSerializers.INT);
    }
}
