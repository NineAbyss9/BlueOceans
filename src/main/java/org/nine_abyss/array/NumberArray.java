
package org.nine_abyss.array;

public class NumberArray<T extends Number>
extends ObjectArray<T>
implements INumberArray {
    @SafeVarargs
    public NumberArray(T... pTs) {
        super(pTs);
    }

    public Number getNumber(int pIndex) {
        return this.convert(pIndex);
    }

    public int intValue(int pIndex) {
        return this.getNumber(pIndex).intValue();
    }

    public float floatValue(int pIndex) {
        return this.getNumber(pIndex).floatValue();
    }

    public double doubleValue(int pIndex) {
        return this.getNumber(pIndex).doubleValue();
    }

    public long longValue(int pIndex) {
        return this.getNumber(pIndex).longValue();
    }
}
