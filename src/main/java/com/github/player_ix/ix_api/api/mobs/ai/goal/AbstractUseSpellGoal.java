
package com.github.player_ix.ix_api.api.mobs.ai.goal;

import com.github.player_ix.ix_api.api.APISpells;
import com.github.player_ix.ix_api.api.mobs.SpellCasterMob;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractUseSpellGoal extends Goal {
    protected final PathfinderMob mob;
    protected final SpellCasterMob caster;
    protected int attackWarmUpDelay;
    protected int nextAttackTickCount;
    public AbstractUseSpellGoal(SpellCasterMob finder) {
        this.mob = (PathfinderMob)finder;
        this.caster = finder;
    }

    @Override
    public boolean canUse() {
        if (!this.checkTarget()) {
            return false;
        }
        if (this.caster.isCastingSpell()) {
            return false;
        }
        return this.mob.tickCount >= this.nextAttackTickCount;
    }

    @Override
    public void start() {
        this.attackWarmUpDelay = reducedTickDelay(this.getCastWarmupTime());
        this.caster.setSpellTick(this.getCastingTime());
        this.nextAttackTickCount = this.mob.tickCount + this.getCastingInterval();
        if (this.getSpellPrepareSound() != null) {
            this.mob.playSound(this.getSpellPrepareSound());
        }
        this.caster.setSpellType(this.getSpell());
    }

    @Override
    public boolean canContinueToUse() {
        return this.attackWarmUpDelay > 0 && this.checkTarget();
    }

    @Override
    public void tick() {
        --this.attackWarmUpDelay;
        if (this.attackWarmUpDelay == 0) {
            this.castSpell();
            if (this.caster.getCastSound() != null) {
                this.mob.playSound(this.caster.getCastSound());
            }
            this.caster.stopSpell();
        }
    }

    public boolean checkTarget() {
        LivingEntity living = this.mob.getTarget();
        return living != null && living.isAlive();
    }

    public int getCastWarmupTime() {
        return 20;
    }

    protected abstract void castSpell();

    protected abstract int getCastingTime();

    protected abstract int getCastingInterval();

    @Nullable
    protected abstract SoundEvent getSpellPrepareSound();

    protected abstract APISpells.APISpell getSpell();
}
