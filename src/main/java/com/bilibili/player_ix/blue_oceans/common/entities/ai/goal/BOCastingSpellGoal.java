
package com.bilibili.player_ix.blue_oceans.common.entities.ai.goal;

import com.bilibili.player_ix.blue_oceans.api.mob.SpellEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BOCastingSpellGoal extends Goal {
    protected final Mob looker;
    protected final SpellEntity caster;
    public BOCastingSpellGoal(Mob mob) {
        this.looker = mob;
        this.caster = (SpellEntity) mob;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    public boolean canUse() {
        return this.caster.isCastingSpell();
    }

    public void start() {
        this.looker.getNavigation().stop();
    }

    public void tick() {
        LivingEntity entity = this.looker.getTarget();
        if (entity != null) {
            this.looker.getLookControl().setLookAt(entity, this.looker.getMaxHeadYRot(), this.looker.getMaxHeadXRot());
        }
    }

    public void stop() {
        this.caster.stopSpell();
    }
}
