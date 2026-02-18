
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.NineAbyss9.annotation.Unused;

@Unused
public class BlueSea
extends AbstractGirl {
    public BlueSea(EntityType<? extends BlueSea> pSea, Level level) {
        super(pSea, level);
    }

    public SpellParticleType getSpellParticleType() {
        return SpellParticleType.SPELL;
    }

    public SoundEvent getCastSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    public void performRangedAttack(LivingEntity livingEntity, float v) {

    }
}
