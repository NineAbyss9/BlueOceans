
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import com.github.NineAbyss9.ix_api.api.mobs.MobFoodData;
import com.github.NineAbyss9.ix_api.api.mobs.ai.goal.MoveToBlocksGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.NineAbyss9.math.MathSupport;

import java.util.List;

/** Small rodent: seed-driven, flees cats and players, raids grain when standing in crops. */
public class Mouse
extends BoAnimal
implements IAnimatedMob, IFlagMob {
    private static final int FLAG_IDLE = 0;
    private static final int FLAG_EATING = 1;
    private static final int EAT_DURATION = 60;
    private static final Ingredient SEED_FOODS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS,
            Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD,
            Items.WHEAT, Items.BREAD);
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    protected static final EntityDataAccessor<Integer> DATA_ANIM_TICK;
    public AnimationState idle = new AnimationState();
    public AnimationState eat = new AnimationState();

    public Mouse(EntityType<? extends Mouse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, FLAG_IDLE);
        this.entityData.define(DATA_ANIM_TICK, 0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.35D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 6.0F, 0.9D, 1.15D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Cat.class, 10.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Ocelot.class, 10.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, ModCat.class, 10.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.85D, SEED_FOODS, false));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new MoveToBlocksGoal(this, 0.65D, 10, Blocks.WHEAT));
        this.goalSelector.addGoal(5, new MoveToBlocksGoal(this, 0.65D, 10, Blocks.CARROTS));
        this.goalSelector.addGoal(5, new MoveToBlocksGoal(this, 0.65D, 10, Blocks.POTATOES));
        this.goalSelector.addGoal(5, new MoveToBlocksGoal(this, 0.65D, 10, Blocks.BEETROOTS));
    }

    protected void clientAiStep() {
        if (this.getFlag() == FLAG_IDLE) {
            this.idle.startIfStopped(this.tickCount);
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS.equals(pKey) && this.level().isClientSide) {
            if (this.getFlag() == FLAG_EATING) {
                this.stopAllAnimations();
                this.eat.startIfStopped(this.tickCount);
            } else {
                this.stopAllAnimations();
                this.idle.startIfStopped(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    public MobFoodData createFoodData() {
        return new MobFoodData(this, 10);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(this.idle, this.eat);
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.tickCropRaiding();
        }
    }

    private void tickCropRaiding() {
        if (this.getFlag() == FLAG_EATING) {
            this.increaseAniTick();
            if (this.aniTick(EAT_DURATION)) {
                this.eatCrops();
                this.resetState();
            }
            return;
        }
        if (this.level().getBlockState(this.blockPosition()).is(BlockTags.CROPS)
                && this.tickCount % 20 == 0
                && MathSupport.threadSafeRandom.nextFloat() < 0.12F) {
            this.setAniTick(0);
            this.setFlag(FLAG_EATING);
        }
    }

    private void eatCrops() {
        BlockState state = this.level().getBlockState(this.blockPosition());
        List<ItemStack> stacks = state.getDrops(new LootParams.Builder((ServerLevel)this.level()));
        if (stacks.stream().anyMatch(ItemStack::isEdible)) {
            stacks.forEach(itemStack -> {
                if (itemStack.isEdible()) {
                    this.eat(this.level(), itemStack);
                }
            });
            this.level().destroyBlock(this.blockPosition(), false, this);
        }
    }

    public boolean isFood(ItemStack pStack) {
        return SEED_FOODS.test(pStack);
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    public int getAniTick() {
        return this.entityData.get(DATA_ANIM_TICK);
    }

    public void setAniTick(int aniTick) {
        this.entityData.set(DATA_ANIM_TICK, aniTick);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.29D)
                .add(Attributes.ATTACK_DAMAGE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 10.0D);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Mouse.class, EntityDataSerializers.INT);
        DATA_ANIM_TICK = SynchedEntityData.defineId(Mouse.class, EntityDataSerializers.INT);
    }
}
