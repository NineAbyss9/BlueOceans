
package org.nine_abyss.array;

public class LongArray
extends NumberArray<Long> {
    public LongArray(Long... pTs) {
        super(pTs);
    }

    public static LongArray of(Long... pLongs) {
        return new LongArray(pLongs);
    }
}
