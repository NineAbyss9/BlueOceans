
package org.NineAbyss9.array;

public class DoubleArray
extends NumberArray<Double> {
    public DoubleArray(Double... pTs) {
        super(pTs);
    }

    public double getDouble(int index) {
        return this.get(index);
    }

    public DoubleArray of(Double... pDoubles) {
        return new DoubleArray(pDoubles);
    }
}
