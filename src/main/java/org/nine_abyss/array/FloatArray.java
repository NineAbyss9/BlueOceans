
package org.nine_abyss.array;

public class FloatArray
extends NumberArray<Float> {
    public FloatArray(Float... pTs) {
        super(pTs);
    }

    public static FloatArray of(Float... pFloats) {
        return new FloatArray(pFloats);
    }
}
