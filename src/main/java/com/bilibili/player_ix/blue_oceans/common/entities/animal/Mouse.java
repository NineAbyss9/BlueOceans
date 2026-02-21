
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.github.NineAbyss9.ix_api.api.mobs.MobFoodData;
import com.github.NineAbyss9.ix_api.api.mobs.ai.goal.MoveToBlocksGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class Mouse
extends BoAnimal
implements IAnimatedMob {
    private int eatTick;
    private boolean eatStarted;
    public AnimationState idle = new AnimationState();
    public AnimationState eat = new AnimationState();
    public Mouse(EntityType<? extends Mouse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0,
                true));
        this.goalSelector.addGoal(3, new MoveToBlocksGoal(this,
                0.7, 10, Blocks.WHEAT));
    }

    public MobFoodData createFoodData() {
        return new MobFoodData(this, 10);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of(eat);
    }

    public void aiStep() {
        super.aiStep();
        if (this.level().getBlockState(this.blockPosition()).is(Blocks.WHEAT)
            && this.level().getRandom().nextInt(3) == 0) {
            eatStarted = true;
        }
        if (eatStarted) {
            ++eatTick;
            if (eatTick >= 60) {
                eatCrops();
                eatStarted = false;
                eatTick = 0;
            }
        }
    }

    private void eatCrops() {
        if (!this.level().isClientSide) {
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
    }
}
