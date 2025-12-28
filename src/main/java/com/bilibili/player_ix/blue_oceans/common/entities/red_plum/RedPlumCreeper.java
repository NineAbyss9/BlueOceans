
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.github.player_ix.ix_api.api.mobs.ICreeper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class RedPlumCreeper
extends RedPlumMonster
implements ICreeper {
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR;
    public RedPlumCreeper(EntityType<? extends RedPlumCreeper> type, Level level) {
        super(type, level);
        this.xpReward = 2;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SWELL_DIR, 0);
    }

    public int getSwellDir() {
        return this.entityData.get(DATA_SWELL_DIR);
    }

    public void setSwellDir(int dir) {
        this.entityData.set(DATA_SWELL_DIR, dir);
    }

    static {
        DATA_SWELL_DIR = SynchedEntityData.defineId(RedPlumCreeper.class, EntityDataSerializers.INT);
    }
}
