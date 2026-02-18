
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior;

import org.NineAbyss9.util.Nothing;
import net.minecraft.util.Mth;

import java.util.EnumSet;

public class Behavior {
    private final EnumSet<BehaviorFlag> flag = EnumSet.noneOf(BehaviorFlag.class);
    public Behavior() {
    }

    public void start() {
    }

    public void tick() {
    }

    public void stop() {
    }

    public boolean canUse() {
        return false;
    }

    public boolean canContinueToUse() {
        return false;
    }

    public boolean requiresUpdateEveryTick() {
        return false;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected Nothing getNothing() {
        return Nothing.getInstance();
    }

    public EnumSet<BehaviorFlag> getFlags() {
        return this.flag;
    }

    protected int adjustedTickDelay(int pAdjustment) {
        return this.requiresUpdateEveryTick() ? pAdjustment : reducedTickDelay(pAdjustment);
    }

    protected static int reducedTickDelay(int pReduction) {
        return Mth.positiveCeilDiv(pReduction, 2);
    }

    public void setFlags(EnumSet<BehaviorFlag> pFlag) {
        this.flag.clear();
        this.flag.addAll(pFlag);
    }
}
