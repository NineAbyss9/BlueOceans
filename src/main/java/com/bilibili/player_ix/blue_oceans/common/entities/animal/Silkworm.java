
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.bilibili.player_ix.blue_oceans.api.mob.CompletelyPerverseState;
import com.bilibili.player_ix.blue_oceans.api.mob.IAnimatedMob;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.flying.AbstractFlyingAnimal;
import com.github.NineAbyss9.ix_api.api.item.ItemStacks;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.List;

/**蝴蝶，包括蚕*/
public class Silkworm
extends AbstractFlyingAnimal
implements IAnimatedMob {
    public static final int DEFAULT_PRODUCE_IN = 600;
    private CompletelyPerverseState perverseState;
    public Silkworm(EntityType<? extends Silkworm> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 9, true);
        this.perverseState = CompletelyPerverseState.LARVA;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.D));
        this.addBehaviorGoals();
    }

    public void aiStep() {
        super.aiStep();
        if (this.perverseState.isLarva() && this.tickCount % DEFAULT_PRODUCE_IN == 0) {
            this.produceSilk();
        }
    }

    public boolean isFlying() {
        if (!this.perverseState.isAdult()) {
            return false;
        }
        return super.isFlying();
    }

    public void setBaby(boolean pBaby) {
        if (pBaby)
            this.perverseState = CompletelyPerverseState.LARVA;
         else
            this.perverseState = CompletelyPerverseState.ADULT;
        super.setBaby(pBaby);
    }

    public List<AnimationState> getAllAnimations() {
        return List.of();
    }

    private void produceSilk() {
        this.spawnAtLocation(ItemStacks.ofRanged(Items.ACACIA_BOAT, 2));
    }
}
