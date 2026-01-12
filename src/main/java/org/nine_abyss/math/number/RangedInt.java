
package org.nine_abyss.math.number;

import org.nine_abyss.math.AbyssMath;

import java.io.Serial;
import java.util.Random;

public abstract class RangedInt
extends Number {
    /**@deprecated */
    @Deprecated
    static final Random random = new Random();
    final int min;
    final int max;
    private RangedInt(int pMin, int pMax) {
        this.min = pMin;
        this.max = pMax;
    }

    public abstract int minValue();

    public float minFloat() {
        return minValue();
    }

    public double minDouble() {
        return minValue();
    }

    public long minLong() {
        return minValue();
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

        public int minValue() {
            return this.min;
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
