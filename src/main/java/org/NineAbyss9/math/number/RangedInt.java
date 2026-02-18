
package org.NineAbyss9.math.number;

import org.NineAbyss9.math.AbyssMath;

import java.io.Serial;
import java.util.Random;

public abstract class RangedInt
extends Number
implements NumberProvider<Integer> {
    /**@deprecated */
    @Deprecated
    static final Random random = new Random();
    final int min;
    final int max;
    private RangedInt(int pMin, int pMax) {
        this.min = pMin;
        this.max = pMax;
    }

    public float minFloat() {
        return min();
    }

    public double minDouble() {
        return min();
    }

    public long minLong() {
        return min();
    }

    public Integer sample() {
        return AbyssMath.randomBetween(min, max);
    }

    public static RangedInt of(int pMin, int pMax) {
        return new Instance(pMin, pMax);
    }

    static class Instance extends RangedInt {
        @Serial
        private static final long serialVersionUID = 2863121246089915556L;
        private Instance(int pMin, int pMax) {
            super(pMin, pMax);
        }

        public Integer min() {
            return this.min;
        }

        public Integer max() {
            return max;
        }

        public int intValue() {
            return max;
        }

        public long longValue() {
            return intValue();
        }

        public float floatValue() {
            return intValue();
        }

        public double doubleValue() {
            return intValue();
        }
    }
}
