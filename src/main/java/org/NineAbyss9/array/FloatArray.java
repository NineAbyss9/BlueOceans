
package org.NineAbyss9.array;

public class FloatArray
extends NumberArray<Float> {
    public FloatArray(Float... pTs) {
        super(pTs);
    }

    public float getFloat(int index) {
        return this.get(index);
    }

    public static FloatArray of(Float... pFloats) {
        return new FloatArray(pFloats);
    }
}
