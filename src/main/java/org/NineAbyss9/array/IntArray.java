
package org.NineAbyss9.array;

public class IntArray
extends NumberArray<Integer> {
    public IntArray(Integer... pTs) {
        super(pTs);
    }

    public int getInt(int index) {
        return this.get(index);
    }

    public static IntArray of(Integer... pIntegers) {
        return new IntArray(pIntegers);
    }
}
