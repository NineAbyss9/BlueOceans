
package com.bilibili.player_ix.blue_oceans.common.entities.ai.goal;

import com.bilibili.player_ix.blue_oceans.api.magic.BOSpellType;
import com.bilibili.player_ix.blue_oceans.api.mob.SpellEntity;
import org.NineAbyss9.util.IXUtil;
import org.NineAbyss9.util.IXUtilUser;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

public abstract class BOUseSpellGoal extends Goal implements IXUtilUser {
    protected final Mob mob;
    protected final SpellEntity caster;
    protected int attackWarmUpDelay;
    protected int nextAttackTickCount;
    public BOUseSpellGoal(SpellEntity finder) {
        this.mob = (Mob)finder;
        this.caster = finder;
    }

    public boolean canUse() {
        if (!this.checkTarget()) {
            return false;
        }
        if (this.caster.isCastingSpell()) {
            return false;
        }
        return this.mob.tickCount >= this.nextAttackTickCount;
    }

    public void start() {
        this.attackWarmUpDelay = reducedTickDelay(this.getCastWarmupTime());
        this.caster.setSpellTick(this.getCastingTime());
        this.nextAttackTickCount = this.mob.tickCount + this.getCastingInterval();
        if (this.getSpellPrepareSound() != null) {
            this.mob.playSound(this.getSpellPrepareSound());
        }
        this.caster.setSpellType(this.getSpell());
    }

    public boolean canContinueToUse() {
        return this.attackWarmUpDelay > 0 && this.checkTarget();
    }

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

    protected <T> T convert() {
        return IXUtil.c.convert(mob);
    }

    protected abstract void castSpell();

    protected abstract int getCastingTime();

    protected abstract int getCastingInterval();

    @Nullable
    protected abstract SoundEvent getSpellPrepareSound();

    protected abstract BOSpellType getSpell();
}
