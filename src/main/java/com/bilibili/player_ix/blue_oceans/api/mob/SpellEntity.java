
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.api.magic.BOSpellType;
import net.minecraft.sounds.SoundEvent;

import javax.annotation.Nullable;

public interface SpellEntity {
    enum SpellParticleType {
        INSTANT,
        SPELL,
        BIG
    }

    boolean isCastingSpell();

    void setSpellTick(int tick);

    SpellParticleType getSpellParticleType();

    BOSpellType getSpellType();

    void setSpellType(BOSpellType type);

    void stopSpell();

    @Nullable
    SoundEvent getCastSound();
}
