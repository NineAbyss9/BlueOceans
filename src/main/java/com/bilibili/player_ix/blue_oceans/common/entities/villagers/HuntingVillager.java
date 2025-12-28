
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.MobTypes;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.github.player_ix.ix_api.api.ApiPose;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.Level;
import org.nine_abyss.util.function.FunctionCollector;

import javax.annotation.Nullable;

public class HuntingVillager
extends BaseVillager {
    public HuntingVillager(EntityType<HuntingVillager> p_35267_, Level p_35268_) {
        super(p_35267_, p_35268_);
    }

    public boolean doHurtTarget(Entity pEntity) {
        if (super.doHurtTarget(pEntity)) {
            this.heal(2f);
            return true;
        }
        return false;
    }

    public void registerBehaviors() {
        this.behaviorSelector.addBehavior(2, new VillagerAttackBehavior(this, 1, true, 3));
        this.behaviorSelector.addBehavior(4, new MoveThroughVillageBehavior(this, 0.8,
                false, 1, FunctionCollector.positiveSupplier()));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, LivingEntity.class,
                15f));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(4, new MoveBackToVillageGoal(this, 0.8,
                true));
        this.targetSelector.addGoal(4, new VillagerHurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this,
                AbstractRedPlumMob.class, false) {
            public boolean canUse() {
                if (this.targetMob instanceof AbstractRedPlumMob ab && ab.getMobTypes() != MobTypes.HOSTILE) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, LivingEntity.class,
                false, (lie) -> lie instanceof Enemy));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return HuntingVillager.createMobAttributes()
                .add(Attributes.ARMOR, 10).add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.ATTACK_KNOCKBACK, 2)
                .add(Attributes.MAX_HEALTH, 40).add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.FOLLOW_RANGE, 90);
    }

    public ApiPose getArmPose() {
        if (this.isAggressive()) {
            return ApiPose.ATTACKING;
        } else {
            return ApiPose.NATURAL;
        }
    }

    @Nullable
    public VillagerTrades.ItemListing[] getTradeLists() {
        return BoVillagerTrades.HUNTER_TRADES;
    }
}
