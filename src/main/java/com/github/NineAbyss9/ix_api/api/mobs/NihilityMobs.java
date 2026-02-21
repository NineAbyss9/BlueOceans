
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class NihilityMobs
extends OwnableMob
implements Nihilistic {
    protected NihilityMobs(EntityType<? extends NihilityMobs> e, Level l) {
        super(e, l);
    }

    public boolean removeWhenFarAway(double d) {
        return d > 50 && this.getOwner() == null;
    }

    public boolean canAttack(LivingEntity pTarget) {
        if (pTarget instanceof Nihilistic) {
            return false;
        }
        return super.canAttack(pTarget);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public boolean addEffect(MobEffectInstance p_147208_, @Nullable Entity p_147209_) {
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return ApiMobType.NIHILISTIC;
    }
}
