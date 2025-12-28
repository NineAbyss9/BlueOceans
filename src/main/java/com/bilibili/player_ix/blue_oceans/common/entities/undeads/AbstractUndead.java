
package com.bilibili.player_ix.blue_oceans.common.entities.undeads;

import com.github.player_ix.ix_api.api.ApiPose;
import com.github.player_ix.ix_api.api.mobs.ApiPoseMob;
import com.github.player_ix.ix_api.api.mobs.OwnableMob;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public abstract class AbstractUndead
extends OwnableMob
implements Enemy, ApiPoseMob {
    protected AbstractUndead(EntityType<? extends AbstractUndead> type, Level level) {
        super(type, level);
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    public ApiPose getPoses() {
        return ApiPose.CROSSED;
    }

    protected static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = AbstractUndead.createMobAttributes();
        builder.add(Attributes.ATTACK_DAMAGE).add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.KNOCKBACK_RESISTANCE);
        return builder;
    }
}
