
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorSelector;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public interface IBehaviorUser {
    void registerBehaviors();

    BehaviorSelector getBehaviorSelector();

    private Entity mySelf() {
        return (Entity)this;
    }

    default void behaviorTick() {
        if (this.mySelf().level() instanceof ServerLevel serverLevel) {
            int i = serverLevel.getServer().getTickCount() + this.mySelf().getId();
            if (i % 2 != 0 && this.mySelf().tickCount > 1) {
                serverLevel.getProfiler().push("behaviorSelector");
                this.getBehaviorSelector().tickRunningBehaviors(false);
                serverLevel.getProfiler().pop();
            } else {
                serverLevel.getProfiler().push("behaviorSelector");
                this.getBehaviorSelector().tick();
                serverLevel.getProfiler().pop();
            }
        }
    }
}
