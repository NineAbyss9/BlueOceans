
package com.github.NineAbyss9.ix_api.api.mobs.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import org.NineAbyss9.util.function.FunctionCollector;

import java.util.function.Predicate;

@SuppressWarnings("unused")
public class ConditionalMeleeAttackGoal<E extends PathfinderMob>
extends ApiMeleeAttackGoal {
    protected Predicate<E> canUseCondition = FunctionCollector.alwaysTrue();
    protected Predicate<E> canContinueToUseCondition = this.canUseCondition;
    /**
     * @param pMob the mob
     * @param pSpeed the speed
     * @param p_25554_ If true, the mob's attack cooldown will depend on its health
     * @param explode will explode
     * @param exp      the explosion damage
     * @param range the attack range
     */
    public ConditionalMeleeAttackGoal(E pMob, double pSpeed, boolean p_25554_, boolean explode, float exp, double range) {
        super(pMob, pSpeed, p_25554_, range);
    }

    public ConditionalMeleeAttackGoal(E mob, double d, boolean cooldownHealth, boolean p_1146) {
        super(mob, d, cooldownHealth);
    }

    public ConditionalMeleeAttackGoal(E mob, double d, boolean b, boolean f, float exp) {
        super(mob, d, b);
    }

    public ConditionalMeleeAttackGoal(E finder, double speed) {
        super(finder, speed);
    }

    public ConditionalMeleeAttackGoal(E finder, double speed, double range) {
        super(finder, speed, range);
    }

    public ConditionalMeleeAttackGoal<E> canUse(Predicate<E> pCondition) {
        this.canUseCondition = pCondition;
        this.canContinueToUseCondition = pCondition;
        return this;
    }

    public ConditionalMeleeAttackGoal<E> canContinueToUse(Predicate<E> pCondition) {
        this.canContinueToUseCondition = pCondition;
        return this;
    }

    public boolean canUse() {
        if (!this.canUseCondition.test(this.convert()))
            return false;
        return super.canUse();
    }

    public boolean canContinueToUse() {
        if (!this.canContinueToUseCondition.test(this.convert()))
            return false;
        return super.canContinueToUse();
    }
}
