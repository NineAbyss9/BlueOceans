
package org.NineAbyss9.math;

import org.NineAbyss9.annotation.doc.ThreadSafe;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**Class to solve math classes.*/
public class MathSupport {
    public static Random random = new Random();
    @ThreadSafe
    public static ThreadLocalRandom threadSafeRandom = ThreadLocalRandom.current();
    public static float rand = threadSafeRandom.nextFloat();

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
