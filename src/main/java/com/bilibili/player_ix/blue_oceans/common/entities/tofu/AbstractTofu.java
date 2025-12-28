
package com.bilibili.player_ix.blue_oceans.common.entities.tofu;

import com.github.player_ix.ix_api.api.mobs.OwnableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public abstract class AbstractTofu extends OwnableMob {
    public AbstractTofu(EntityType<? extends AbstractTofu> type, Level level) {
        super(type, level);
    }

    public boolean canAttack(LivingEntity pTarget) {
        if (pTarget instanceof AbstractTofu) {
            return false;
        }
        return super.canAttack(pTarget);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractTofu.createMobAttributes()
                .add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE);
    }
}
