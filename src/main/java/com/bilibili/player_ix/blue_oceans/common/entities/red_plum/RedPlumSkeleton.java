
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.github.player_ix.ix_api.api.ApiPose;
import com.github.player_ix.ix_api.api.item.ItemStacks;
import com.github.player_ix.ix_api.api.mobs.ApiRangedAttackMob;
import com.github.player_ix.ix_api.api.mobs.ai.goal.ApiRangedBowAttackGoal;
import com.github.player_ix.ix_api.api.mobs.effect.EffectInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class RedPlumSkeleton
extends RedPlumMonster
implements ApiRangedAttackMob {
    public RedPlumSkeleton(EntityType<? extends RedPlumSkeleton> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.setItemInHand(InteractionHand.MAIN_HAND, ItemStacks.of(Items.BOW));
        this.xpReward = 2;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ApiRangedBowAttackGoal(this, 1.0,
                10, 25f));
        super.registerGoals();
    }

    public AbstractArrow getArrow(ItemStack stack, float fall) {
        Arrow arrow = new Arrow(this.level(), this);
        arrow.setEffectsFromItem(stack);
        arrow.addEffect(EffectInstance.create(BlueOceansMobEffects.PLUM_INVADE, 100, 0));
        return arrow;
    }

    public ApiPose getPoses() {
        if (this.isAggressive())
            if (this.isHoldingBow())
                return ApiPose.BOW_AND_ARROW;
            else
                return ApiPose.ZOMBIE_ATTACKING;
        else
            return ApiPose.NATURAL;
    }


    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.ARMOR, 4).add(Attributes.KNOCKBACK_RESISTANCE, 0.15)
                .add(Attributes.FOLLOW_RANGE, 42).add(Attributes.MAX_HEALTH, 24);
    }
}
