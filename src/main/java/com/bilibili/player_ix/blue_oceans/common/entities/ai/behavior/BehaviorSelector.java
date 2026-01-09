
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BehaviorSelector {
    private final Set<BehaviorWrapper> availableBehaviors = Sets.newLinkedHashSet();
    private static final BehaviorWrapper NO_BEHAVIOR = BehaviorWrapper.empty();
    private final Supplier<ProfilerFiller> profiler;
    private final Map<BehaviorFlag, BehaviorWrapper> lockedFlags = Maps.newEnumMap(BehaviorFlag.class);
    private final EnumSet<BehaviorFlag> disabledFlags = EnumSet.noneOf(BehaviorFlag.class);
    public BehaviorSelector(Supplier<ProfilerFiller> filler) {
        profiler = filler;
    }

    public void addBehavior(int p, Behavior behavior) {
        this.availableBehaviors.add(new BehaviorWrapper(p, behavior));
    }

    public void removeBehavior(int p, BehaviorWrapper behavior) {
        this.availableBehaviors.remove(behavior);
    }

    public void removeAll() {
        this.availableBehaviors.clear();
    }

    public void removeIf(Predicate<BehaviorWrapper> predicate) {
        this.availableBehaviors.removeIf(predicate);
    }

    private static boolean containsAnyFlags(BehaviorWrapper pGoal, EnumSet<BehaviorFlag> pFlag) {
        Iterator<BehaviorFlag> var2 = pGoal.getFlags().iterator();
        BehaviorFlag $$2;
        do {
            if (!var2.hasNext()) {
                return false;
            }
            $$2 = var2.next();
        } while(!pFlag.contains($$2));
        return true;
    }

    private static boolean canBeReplacedForAllFlags(BehaviorWrapper pGoal, Map<BehaviorFlag,
            BehaviorWrapper> pFlag) {
        Iterator<BehaviorFlag> var2 = pGoal.getFlags().iterator();
        BehaviorFlag $$2;
        do {
            if (!var2.hasNext()) {
                return true;
            }
            $$2 = var2.next();
        } while ((pFlag.getOrDefault($$2, NO_BEHAVIOR)).canBeReplacedBy(pGoal));
        return false;
    }

    public void tickRunningBehaviors(boolean pTickAllRunning) {
        ProfilerFiller $$1 = this.profiler.get();
        $$1.push("behaviorSelectorTick");
        Iterator<?> var3 = this.availableBehaviors.iterator();
        while (true) {
            BehaviorWrapper $$2;
            do {
                do {
                    if (!var3.hasNext()) {
                        $$1.pop();
                        return;
                    }
                    $$2 = (BehaviorWrapper)var3.next();
                } while(!$$2.isRunning());
            } while(!pTickAllRunning && !$$2.requiresUpdateEveryTick());
            $$2.tick();
        }
    }

    @SuppressWarnings("unchecked")
    public void tick() {
        ProfilerFiller $$0 = this.profiler.get();
        $$0.push("behaviorCleanup");
        Iterator<?> $$2 = this.availableBehaviors.iterator();
        while(true) {
            BehaviorWrapper wrapper;
            do {
                do {
                    if (!$$2.hasNext()) {
                        $$2 = this.lockedFlags.entrySet().iterator();
                        while ($$2.hasNext()) {
                            Map.Entry<BehaviorFlag, BehaviorWrapper> $$3 =
                                    (Map.Entry<BehaviorFlag, BehaviorWrapper>)$$2.next();
                            if (!$$3.getValue().isRunning()) {
                                $$2.remove();
                            }
                        }
                        $$0.pop();
                        $$0.push("behaviorUpdate");
                        $$2 = this.availableBehaviors.iterator();
                        while (true) {
                            do {
                                do {
                                    do {
                                        do {
                                            if (!$$2.hasNext()) {
                                                $$0.pop();
                                                this.tickRunningBehaviors(true);
                                                return;
                                            }
                                            wrapper = (BehaviorWrapper)$$2.next();
                                        } while(wrapper.isRunning());
                                    } while(containsAnyFlags(wrapper, this.disabledFlags));
                                } while(!canBeReplacedForAllFlags(wrapper, this.lockedFlags));
                            } while(!wrapper.canUse());
                            for (BehaviorFlag $$5 : wrapper.getFlags()) {
                                BehaviorWrapper $$6 = this.lockedFlags.getOrDefault($$5, NO_BEHAVIOR);
                                $$6.stop();
                                this.lockedFlags.put($$5, wrapper);
                            }
                            wrapper.start();
                        }
                    }
                    wrapper = (BehaviorWrapper)$$2.next();
                } while (!wrapper.isRunning());
            } while (!containsAnyFlags(wrapper, this.disabledFlags) && wrapper.canContinueToUse());
            wrapper.stop();
        }
    }
}
