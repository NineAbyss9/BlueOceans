
package com.bilibili.player_ix.blue_oceans.common.entities.undeads;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class UndeadBoss
extends Monster {
    protected int spellTicks;
    protected UndeadBoss(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public enum BossPose {
        BOW_AND_ARROW,
        CASTING_SPELL,
        NATURAL,
        STAFF_ATTACKING
    }

    protected void customServerAiStep() {
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isCastingSpell()) {
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX(), this.getY() + 1.8, this.getZ(),
                    0, 0.1, 0);
        }
    }

    protected BossPose getBossPose() {
        return BossPose.NATURAL;
    }

    protected boolean isCastingSpell() {
        return this.spellTicks > 0;
    }
}
