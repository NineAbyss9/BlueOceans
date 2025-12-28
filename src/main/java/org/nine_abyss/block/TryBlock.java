
package org.nine_abyss.block;

import org.nine_abyss.cache.Cache;

public class TryBlock {
    private final Runnable runnable;
    public TryBlock(Runnable work) {
        this.runnable = work;
    }

    public boolean run() {
        try {
            runnable.run();
        } catch (Exception e) {
            Cache.add(e);
            return false;
        }
        return true;
    }
}
