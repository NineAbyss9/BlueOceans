
package org.nine_abyss.math;

import java.util.Random;

/**Class to solve math classes.*/
public class MathSupport {
    public static Random random = new Random();

    public static class Cos {
    }

    public static class Lerp {
        private float value;
        private double doubleValue;
        public Lerp() {
        }

        public Lerp(float delta, float start, float end) {
            run(delta, start, end);
        }

        public Lerp(double delta, double start, double end) {
            run(delta, start, end);
        }

        public void run(float delta, float start, float end) {
            value = start + delta * (end - start);
        }

        public void run(double delta, double start, double end) {
            doubleValue = start + delta * (end - start);
        }

        public float floatValue() {
            return value;
        }

        public double doubleValue() {
            return doubleValue;
        }
    }
}
