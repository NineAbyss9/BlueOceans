
package org.nine_abyss.math.number;

import org.nine_abyss.math.AbyssMath;

import java.util.Random;

public abstract class RangedFloat
extends Number {
    /**@deprecated */
    @Deprecated
    static final Random random = new Random();
    public final Float min;
    public final Float max;
    private RangedFloat(Float pMin, Float pMax) {
        this.min = pMin;
        this.max = pMax;
    }

    public Float sample() {
        return AbyssMath.randomBetween(min, max);
    }

    public Float min() {
        return min;
    }

    public Float max() {
        return max;
    }

    public static RangedFloat of(Float pMin, Float pMax) {
        if (pMin > pMax)
            throw new IllegalArgumentException("max must be greater than min");
        return new Instance(pMin, pMax);
    }

    static class Instance extends RangedFloat {
        Instance(Float pMin, Float pMax) {
            super(pMin, pMax);
        }

        public int intValue() {
            return sample().intValue();
        }

        public long longValue() {
            return sample().longValue();
        }

        public float floatValue() {
            return sample();
        }

        public double doubleValue() {
            return sample().doubleValue();
        }
    }
}
