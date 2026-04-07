
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.github.NineAbyss9.ix_api.api.mobs.IConversion;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class UnformedPlum
extends RedPlumMonster
implements IConversion {
    private static final EntityDataAccessor<Integer> CONVERSION_TICK;

    public UnformedPlum(EntityType<? extends UnformedPlum> type, Level level) {
        super(type, level);
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

    }

    public int getLevel() {
        return 0;
    }

    static {
        CONVERSION_TICK = SynchedEntityData.defineId(UnformedPlum.class, EntityDataSerializers.INT);
    }
}
