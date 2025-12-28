
package org.nine_abyss.array;

public class IntArray
extends NumberArray<Integer> {
    public IntArray(Integer... pTs) {
        super(pTs);
    }

    public static IntArray of(Integer... pIntegers) {
        return new IntArray(pIntegers);
    }
}
