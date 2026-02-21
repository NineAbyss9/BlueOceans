
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.NineAbyss9.annotation.doc.Since;
import org.NineAbyss9.math.MathSupport;

@Since("1.1.3b")
public class RedPlumVillager
extends RedPlumMonster {
    public RedPlumVillager(EntityType<? extends RedPlumVillager> type, Level level) {
        super(type, level);
    }

    protected void registerGoals() {
        this.addMeleeAttackGoal(1, 1, 2F);
        super.registerGoals();
    }

    protected void populateDefaultItems() {
        if (MathSupport.random.nextFloat() < 0.3F)
            this.setMainHandItem(Items.WOODEN_AXE);
    }

    public ApiPose getPoses() {
        if (this.isAggressive())
            return ApiPose.ZOMBIE_ATTACKING;
        return ApiPose.NATURAL;
    }

    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return BlueOceansEntities.RED_PLUM_SLAYER.get();
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    public float getVoicePitch() {
        return 0.5F;
    }

    public static AttributeSupplier createAttributes() {
        return createPathAttributes().add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.ARMOR, 2).add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 24).add(Attributes.MAX_HEALTH, 24).build();
    }
}
