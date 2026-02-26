
package com.bilibili.player_ix.blue_oceans.common.entities.illagers;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class ModSpellcasterIllager
extends AbstractIllager {
    protected static final EntityDataAccessor<Integer> SPELL =
            SynchedEntityData.defineId(ModSpellcasterIllager.class, EntityDataSerializers.INT);
    protected int spellTicks;
    protected SpellType currentSpell = SpellType.NONE;

    protected ModSpellcasterIllager(EntityType<? extends AbstractIllager> p_32105_, Level p_32106_) {
        super(p_32105_, p_32106_);
        this.xpReward = 10;
    }

    public int getSpellTicks() {
        return this.spellTicks;
    }

    public void setSpellTicks(int ticks) {
        this.spellTicks = ticks;
    }

    public boolean isCastingSpell() {
        if (this.level().isClientSide) {
            return this.entityData.get(SPELL) > 0;
        }
        return this.spellTicks > 0;
    }

    protected void setSpellType(SpellType spellType) {
        this.currentSpell = spellType;
        this.entityData.set(SPELL, spellType.id);
    }

    protected SpellType getSpellId() {
        return SpellType.getById(this.entityData.get(SPELL));
    }

    public void readAdditionalSaveData(CompoundTag pTag) {
        super.readAdditionalSaveData(pTag);
        this.spellTicks = pTag.getInt("SpellTicks");
    }

    public void addAdditionalSaveData(CompoundTag pTag) {
        super.addAdditionalSaveData(pTag);
        pTag.putInt("SpellTicks", this.spellTicks);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isCastingSpell() && this.isAlive()) {
            SpellType spellId = this.getSpellId();
            double $$1 = spellId.spellColor[0];
            double $$2 = spellId.spellColor[1];
            double $$3 = spellId.spellColor[2];
            float $$4 = this.yBodyRot * Maths.PI_DIVIDING_180 + Mth.cos(this.tickCount * 0.6662f) * 0.25f;
            float $$5 = Mth.cos($$4);
            float $$6 = Mth.sin($$4);
            double d = 1.8;
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + $$5 * 0.6, this.getY() + d,
                    this.getZ() + $$6 * 0.6, $$1, $$2, $$3);
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() - $$5 * 0.6, this.getY() + d,
                    this.getZ() - $$6 * 0.6, $$1, $$2, $$3);
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPELL, 0);
    }

    public enum SpellType {
        NONE(0, 0, 0, 0),
        DARK(1, 0.01, 0, 0),
        NATURAL(2, 0.3, 0.9, 0.3),
        WATER(3, 0.3, 0.3, 0.8);

        private final int id;
        private final double[] spellColor;

        SpellType(int t, double xSpeed, double ySpeed, double zSpeed) {
            this.id = t;
            this.spellColor = new double[] {xSpeed, ySpeed, zSpeed
            };
        }

        public static SpellType getById(int nt) {
            for (SpellType spellType : SpellType.values()) {
                if (nt != spellType.id) continue;
                return spellType;
            }
            return NONE;
        }
    }

    protected class CastingSpellGoal
    extends Goal {
        ModSpellcasterIllager illager = ModSpellcasterIllager.this;

        CastingSpellGoal(){
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
        }

        public boolean canUse() {
            return illager.getSpellTicks() > 0;
        }

        public void start() {
            super.start();
            illager.navigation.stop();
        }

        public void stop() {
            super.stop();
            illager.setSpellType(SpellType.NONE);
        }

        public void tick() {
            super.tick();
            if (illager.getTarget() != null) {
                illager.lookControl.setLookAt(illager.getTarget());
            }
        }
    }

    protected SoundEvent getSpellCastSoundEvent() {
        return BlueOceansSounds.RED_PLUM_CULTIST_CAST_SPELL.get();
    }

    protected abstract class UseSpellGoal
    extends Goal {
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;

        UseSpellGoal() {
        }

        public void start() {
            this.attackWarmupDelay = this.adjustedTickDelay(this.getCastWarmupTime());
            setSpellTicks(this.getCastingTime());
            this.nextAttackTickCount = tickCount + this.getCastingInterval();
            SoundEvent $$0 = this.getSpellPrepareSound();
            if ($$0 != null) {
                playSound($$0, 1f, 1f);
            }
            setSpellType(this.getSpell());
        }

        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.castSpell();
                playSound(getSpellCastSoundEvent(), 1f, 1f);
            }
        }

        public boolean canUse() {
            if (getTarget() == null || !getTarget().isAlive()) {
                return false;
            }
            if (isCastingSpell()) {
                return false;
            }
            return tickCount >= this.nextAttackTickCount;
        }

        public boolean canContinueToUse() {
            return this.attackWarmupDelay > 0 && getTarget() != null;
        }

        protected abstract void castSpell();
        protected abstract SpellType getSpell();
        @Nullable
        protected abstract SoundEvent getSpellPrepareSound();
        protected abstract int getCastingTime();
        protected abstract int getCastingInterval();

        protected int getCastWarmupTime() {
            return 20;
        }
    }
}
