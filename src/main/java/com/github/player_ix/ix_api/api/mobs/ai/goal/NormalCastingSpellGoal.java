
package com.github.player_ix.ix_api.api.mobs.ai.goal;

import com.github.player_ix.ix_api.api.mobs.SpellCasterMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class NormalCastingSpellGoal extends Goal {
    protected final Mob looker;
    protected final SpellCasterMob caster;

    public NormalCastingSpellGoal(Mob mob) {
        this.looker = mob;
        this.caster = (SpellCasterMob)mob;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.caster.isCastingSpell();
    }

    @Override
    public void start() {
        this.looker.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity entity = this.looker.getTarget();
        if (entity != null) {
            this.looker.getLookControl().setLookAt(entity, this.looker.getMaxHeadYRot(), this.looker.getMaxHeadXRot());
        }
    }

    @Override
    public void stop() {
        this.caster.stopSpell();
    }
}
