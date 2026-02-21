
package com.github.NineAbyss9.ix_api.api.mobs;

import com.github.NineAbyss9.ix_api.api.APISpells;
import net.minecraft.sounds.SoundEvent;

import javax.annotation.Nullable;

public interface SpellCasterMob {
    void setSpellType(APISpells.APISpell spell);

    void setSpellTick(int tick);

    boolean isCastingSpell();

    @Nullable
    SoundEvent getCastSound();

    default void stopSpell() {
        this.setSpellType(APISpells.APISpell.NONE);
        this.setSpellTick(0);
    }
}
