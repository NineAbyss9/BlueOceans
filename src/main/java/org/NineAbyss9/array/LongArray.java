
package org.NineAbyss9.array;

public class LongArray
extends NumberArray<Long> {
    public LongArray(Long... pTs) {
        super(pTs);
    }

    public static LongArray of(Long... pLongs) {
        return new LongArray(pLongs);
    }
}
