
package com.bilibili.player_ix.blue_oceans.common.entities.dumplings;

import com.github.NineAbyss9.ix_api.api.mobs.OwnableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import static net.minecraft.world.entity.monster.Enemy.XP_REWARD_SMALL;

public abstract class AbstractDumpling
extends OwnableMob {
    protected AbstractDumpling(EntityType<? extends OwnableMob> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = XP_REWARD_SMALL;
    }

    protected static AttributeSupplier.Builder createAttributes() {
        return AbstractDumpling.createMobAttributes().add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.FOLLOW_RANGE).add(Attributes.KNOCKBACK_RESISTANCE);
    }
}
