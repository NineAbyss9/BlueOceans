
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.github.NineAbyss9.ix_api.api.mobs.IConversion;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class UnformedPlum
extends RedPlumMonster
implements IConversion {
    private static final EntityDataAccessor<Integer> CONVERSION_TICK;
    public UnformedPlum(EntityType<? extends UnformedPlum> type, Level level) {
        super(type, level);
        this.xpReward = 1;
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return null;
    }

    protected int nextConvertUpNeeds() {
        return Integer.MAX_VALUE;
    }

    public boolean isConverting() {
        return this.getConversionTick() > 0;
    }

    public int getConversionTick() {
        return this.entityData.get(CONVERSION_TICK);
    }

    public void setConversionTick(int i) {
        this.entityData.set(CONVERSION_TICK, i);
    }

    public void performConvert() {
        if (this.isServerSide()) {
            AbstractRedPlumMob entity = null;
            entity.moveTo(this.position());
            if (this.level().addFreshEntity(entity)) {
                this.discard();
            }
        }
    }

    public int getLevel() {
        return 0;
    }

    static {
        CONVERSION_TICK = SynchedEntityData.defineId(UnformedPlum.class, EntityDataSerializers.INT);
    }
}
