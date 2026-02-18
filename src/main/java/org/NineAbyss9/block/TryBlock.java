
package org.NineAbyss9.block;

import org.NineAbyss9.cache.Cache;

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
