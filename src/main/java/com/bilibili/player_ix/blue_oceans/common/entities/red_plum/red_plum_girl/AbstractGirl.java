
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl;

import com.bilibili.player_ix.blue_oceans.api.magic.BOSpellType;
import com.bilibili.player_ix.blue_oceans.common.entities.AbstractBlueOceansMob;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.bilibili.player_ix.blue_oceans.api.mob.SpellEntity;
import com.github.player_ix.ix_api.util.Maths;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;

public abstract class AbstractGirl
extends AbstractBlueOceansMob
implements RangedAttackMob, SpellEntity {
    protected static final EntityDataAccessor<Integer> SPELL =
            SynchedEntityData.defineId(AbstractGirl.class, EntityDataSerializers.INT);
    public int spellTicks;
    protected BOSpellType currentSpell = BOSpellType.NONE;
    protected AbstractGirl(EntityType<? extends AbstractGirl> pGirl, Level level) {
        super(pGirl, level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPELL, 0);
    }

    public void readAdditionalSaveData(CompoundTag $$0) {
        super.readAdditionalSaveData($$0);
        this.spellTicks = $$0.getInt("SpellTicks");
    }

    public void addAdditionalSaveData(CompoundTag $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putInt("SpellTicks", this.spellTicks);
    }

    public void aiStep() {
        super.aiStep();
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isCastingSpell()) {
            BOSpellType spellId = this.getSpellType();
            double $$1 = spellId.spellColor[0];
            double $$2 = spellId.spellColor[1];
            double $$3 = spellId.spellColor[2];
            float $$4 = this.yBodyRot * Maths.PI_DIVIDING_180 + Mth.cos(this.tickCount * 0.6662f) * 0.25f;
            float $$5 = Mth.cos($$4);
            float $$6 = Mth.sin($$4);
            switch (this.getSpellParticleType()) {
                case INSTANT: {
                    this.level().addParticle(BlueOceansParticleTypes.RED_PLUM_INSTANT_SPELL.get(),
                            this.getX() + $$5 * 0.6, this.getY() + 1.8, this.getZ() + $$6 * 0.6, $$1, $$2, $$3);
                    break;
                }
                case SPELL: {
                    this.level().addParticle(BlueOceansParticleTypes.RED_PLUM_SPELL.get(), this.getX() +
                            $$5 * 0.6, this.getY() + 1.8, this.getZ() + $$6 * 0.6, $$1, $$2, $$3);
                    break;
                }
            }
        }
    }

    public boolean isCastingSpell() {
        if (this.level().isClientSide) {
            return this.entityData.get(SPELL) > 0;
        }
        return this.getSpellTick() > 0;
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellTicks > 0) {
            this.spellTicks--;
        }
    }

    public void setSpellType(BOSpellType type) {
        this.currentSpell = type;
        this.entityData.set(SPELL, type.id);
    }

    public void stopSpell() {
        this.spellTicks = 0;
        this.currentSpell = BOSpellType.NONE;
        this.setSpellType(BOSpellType.NONE);
    }

    public BOSpellType getSpellType() {
        if (this.level().isClientSide) {
            return this.currentSpell;
        }
        return BOSpellType.getById(this.entityData.get(SPELL));
    }

    public int getSpellTick() {
        return this.spellTicks;
    }

    public void setSpellTick(int tick) {
        this.spellTicks = tick;
    }
}
