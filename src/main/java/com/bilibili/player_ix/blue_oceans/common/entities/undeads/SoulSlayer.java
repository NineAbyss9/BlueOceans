
package com.bilibili.player_ix.blue_oceans.common.entities.undeads;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SoulSlayer
extends AbstractUndead
implements RangedAttackMob {
    public SoulSlayer(EntityType<SoulSlayer> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.xpReward = XP_REWARD_LARGE;
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1,
                true));
        this.addBehaviorGoal(4, 1, 10F);
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this, AbstractUndead.class).setAlertOthers());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Mob.class,
                false, livingEntity -> livingEntity.getMobType() != MobType.UNDEAD));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class,
                true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = SoulSlayer.createMobAttributes();
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(Attributes.ATTACK_KNOCKBACK, 2).add(Attributes.ATTACK_DAMAGE, 7);
        builder.add(Attributes.ARMOR, 10).add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
        builder.add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.FOLLOW_RANGE, 100);
        return builder;
    }

    public void performRangedAttack(LivingEntity livingEntity, float v) {

    }
}
