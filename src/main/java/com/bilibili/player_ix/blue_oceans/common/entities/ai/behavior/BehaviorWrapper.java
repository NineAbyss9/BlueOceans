
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BehaviorWrapper {
    private final Behavior behavior;
    private final int priority;
    private boolean isRunning;

    public BehaviorWrapper(int pPriority, Behavior pBehavior) {
        this.priority = pPriority;
        this.behavior = pBehavior;
    }

    public boolean start() {
        if (!this.isRunning()) {
            this.isRunning = true;
            this.behavior.start();
            return true;
        } else
            return false;
    }

    public void tick() {
        this.behavior.tick();
    }

    public void stop() {
        if (this.isRunning()) {
            this.isRunning = false;
            this.behavior.stop();
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public int getPriority() {
        return this.priority;
    }

    public Behavior getBehavior() {
        return this.behavior;
    }

    public EnumSet<BehaviorFlag> getFlags() {
        return this.behavior.getFlags();
    }

    public void setFlags(EnumSet<BehaviorFlag> pFlag) {
        this.behavior.setFlags(pFlag);
    }

    public boolean canBeReplacedBy(BehaviorWrapper wrapper) {
        return wrapper.getPriority() < this.getPriority();
    }

    public boolean requiresUpdateEveryTick() {
        return this.behavior.requiresUpdateEveryTick();
    }

    public boolean canUse() {
        return this.behavior.canUse();
    }

    public boolean canContinueToUse() {
        return this.behavior.canContinueToUse();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        else {
            if (obj == null)
                return false;
            return obj instanceof BehaviorWrapper wrapper && wrapper.behavior.equals(behavior);
        }
    }

    public int hashCode() {
        return this.behavior.hashCode();
    }

    public static BehaviorWrapper empty() {
        return new BehaviorWrapper(Integer.MAX_VALUE, new Behavior()) {
            public boolean isRunning() {
                return false;
            }
        };
    }
}
