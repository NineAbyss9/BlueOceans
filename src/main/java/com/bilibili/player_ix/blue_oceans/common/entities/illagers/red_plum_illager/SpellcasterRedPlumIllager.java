
package com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager;

import com.bilibili.player_ix.blue_oceans.api.magic.BOSpellType;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansSounds;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.api.mob.SpellEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SpellcasterRedPlumIllager extends RedPlumIllager implements SpellEntity {
    protected static final EntityDataAccessor<Byte> SPELL = SynchedEntityData.defineId(SpellcasterRedPlumIllager.class, EntityDataSerializers.BYTE);
    protected int spellTicks;
    private BOSpellType currentSpell = BOSpellType.NONE;

    protected SpellcasterRedPlumIllager(EntityType<? extends AbstractRedPlumMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isCastingSpell()) {
            BOSpellType spellId = this.getSpellType();
            double $$1 = spellId.spellColor[0];
            double $$2 = spellId.spellColor[1];
            double $$3 = spellId.spellColor[2];
            float $$4 = this.yBodyRot * ((float) Math.PI / 180) + Mth.cos((float) this.tickCount * 0.6662f) * 0.25f;
            float $$5 = Mth.cos($$4);
            float $$6 = Mth.sin($$4);
            if (this.getSpellParticleType() == SpellParticleType.SPELL) {
                this.level().addParticle(BlueOceansParticleTypes.RED_PLUM_SPELL.get(), this.getX() + (double)$$5 * 0.6, this.getY() + 1.8, this.getZ() + (double) $$6 * 0.6, $$1, $$2, $$3);
                this.level().addParticle(BlueOceansParticleTypes.RED_PLUM_SPELL.get(), this.getX() - (double)$$5 * 0.6, this.getY() + 1.8, this.getZ() - (double) $$6 * 0.6, $$1, $$2, $$3);
            } else if (this.getSpellParticleType() == SpellParticleType.BIG) {
                this.level().addParticle(BlueOceansParticleTypes.BIG_RED_PLUM_INSTANT_SPELL.get(), this.getX() + (double) $$5 * 0.6, this.getY() + 1.8, this.getZ() + (double) $$6 * 0.6, $$1, $$2, $$3);
                this.level().addParticle(BlueOceansParticleTypes.BIG_RED_PLUM_INSTANT_SPELL.get(), this.getX() - (double) $$5 * 0.6, this.getY() + 1.8, this.getZ() - (double) $$6 * 0.6, $$1, $$2, $$3);
            } else if (this.getSpellParticleType() == SpellParticleType.INSTANT) {
                this.level().addParticle(BlueOceansParticleTypes.RED_PLUM_INSTANT_SPELL.get(), this.getX() + (double) $$5 * 0.6, this.getY() + 1.8, this.getZ() + (double) $$6 * 0.6, $$1, $$2, $$3);
                this.level().addParticle(BlueOceansParticleTypes.RED_PLUM_INSTANT_SPELL.get(), this.getX() - (double) $$5 * 0.6, this.getY() + 1.8, this.getZ() - (double) $$6 * 0.6, $$1, $$2, $$3);
            }
        }
    }

    public SpellParticleType getSpellParticleType() {
        return SpellParticleType.SPELL;
    }

    public BOSpellType getSpellType() {
        return this.currentSpell;
    }

    @Override
    public void setSpellType(BOSpellType type) {
        this.currentSpell = type;
        this.entityData.set(SPELL, (byte)type.id);
    }

    @Override
    public void stopSpell() {
        this.spellTicks = 0;
        this.setSpellType(BOSpellType.NONE);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPELL, (byte) 0);
    }

    protected class CastingSpellGoal
    extends Goal {
        SpellcasterRedPlumIllager illager = SpellcasterRedPlumIllager.this;

        public CastingSpellGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return illager.getSpellCastingTime() > 0;
        }

        @Override
        public void start() {
            super.start();
            illager.navigation.stop();
        }

        @Override
        public void stop() {
            super.stop();
            illager.setSpellType(BOSpellType.NONE);
        }

        @Override
        public void tick() {
            if (illager.getTarget() != null) {
                illager.getLookControl().setLookAt(illager.getTarget(), illager.getMaxHeadYRot(), illager.getMaxHeadXRot());
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag $$0) {
        super.readAdditionalSaveData($$0);
        this.spellTicks = $$0.getInt("SpellTicks");
        this.setSpellType(BOSpellType.getById($$0.getInt("Spell")));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putInt("SpellTicks", this.spellTicks);
        $$0.putInt("Spell", this.getSpellType().id);
    }

    public SoundEvent getCastSound() {
        return BlueOceansSounds.RED_PLUM_CULTIST_CAST_SPELL.get();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
    }

    protected abstract class UseSpellGoal  extends Goal {
        SpellcasterRedPlumIllager illager = SpellcasterRedPlumIllager.this;
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;

        @Override
        public void start() {
            this.attackWarmupDelay = this.adjustedTickDelay(this.getCastWarmupTime());
            illager.spellTicks = this.getCastingTime();
            this.nextAttackTickCount = illager.tickCount + this.getCastingInterval();
            SoundEvent $$0 = this.getSpellPrepareSound();
            if ($$0 != null) {
                illager.playSound($$0, 1f, 1f);
            }
            illager.setSpellType(this.getSpell());
        }

        @Override
        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.performSpellCasting();
                illager.playSound(illager.getCastSound(), 1f, 1f);
            }
        }

        @Override
        public boolean canUse() {
            if (illager.getTarget() == null || !illager.getTarget().isAlive()) {
                return false;
            }
            if (illager.isCastingSpell()) {
                return false;
            }
            return illager.tickCount >= this.nextAttackTickCount;
        }

        @Override
        public boolean canContinueToUse() {
            return this.attackWarmupDelay > 0 && illager.getTarget() != null;
        }

        protected abstract void performSpellCasting();

        protected int getCastWarmupTime() {
            return 20;
        }

        protected abstract int getCastingTime();

        protected abstract int getCastingInterval();

        @Nullable
        protected abstract SoundEvent getSpellPrepareSound();

        protected abstract BOSpellType getSpell();
    }

    public boolean isCastingSpell() {
        if (this.level().isClientSide) {
            return this.entityData.get(SPELL) > 0;
        }
        return this.spellTicks != 0;
    }

    @Override
    public void setSpellTick(int tick) {
        this.spellTicks = tick;
    }

    protected int getSpellCastingTime() {
        return this.spellTicks;
    }
}
