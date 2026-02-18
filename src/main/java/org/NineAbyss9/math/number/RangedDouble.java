
package org.NineAbyss9.math.number;

import org.NineAbyss9.math.AbyssMath;

import java.io.Serial;

public abstract class RangedDouble
extends Number
implements NumberProvider<Double> {
    public final Double max;
    public final Double min;
    private RangedDouble(Double pMax, Double pMin) {
        max = pMax;
        min = pMin;
    }

    public Double max() {
        return max;
    }

    public Double min() {
        return min;
    }

    public Double sample() {
        return AbyssMath.randomBetween(min, max);
    }

    public static RangedDouble of(double pMax, double pMin) {
        return new Instance(pMax, pMin);
    }

    private static class Instance extends RangedDouble {
        @Serial
        private static final long serialVersionUID = 1176660604427700176L;
        private Instance(Double pMax, Double pMin) {
            super(pMax, pMin);
        }

        public int intValue() {
            return sample().intValue();
        }

        public long longValue() {
            return sample().longValue();
        }

        public float floatValue() {
            return sample().floatValue();
        }

        public double doubleValue() {
            return sample().doubleValue();
        }
    }
}
