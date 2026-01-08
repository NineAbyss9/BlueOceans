
package org.nine_abyss.math;

import org.nine_abyss.annotation.doc.Message;

import java.util.Random;

/**Class for math.*/
public class AbyssMath {
    @org.nine_abyss.annotation.NotCheck
    public static final int TRUTH = 9;
    /**Holds the Pi value.*/
    public static final Float PI = 3.1415926535897932384626F;
    /**@see Math#E*/
    public static final Float E = (float)Math.E;
    public static final Random random = new Random();

    //Base
    public static byte toByte(int v) {
        return (byte)v;
    }

    public static boolean toBoolean(int i) {
        return i != 0;
    }

    public static boolean toBoolean(float f) {
        return f != 0;
    }

    public static boolean toBoolean(double d) {
        return d != 0;
    }

    public static float multiply(float a, float b) {
        return a * b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static float square(float a) {
        return toThePowerOf(a, 2);
    }

    public static float toThePowerOf(float root, int index) {
        return toThePowerOf(root, (float)index);
    }

    public static float toThePowerOf(float root, float index) {
        float base = root;
        int cache = 0;
        for (;cache < index;cache++)
            base *= root;
        base += negate((float)cache - index) * root;
        return base;
    }

    public static int negate(int value) {
        return -value;
    }

    public static float negate(float value) {
        return -value;
    }

    public static double negate(double value) {
        return -value;
    }

    public static float absMax(float a, float b) {
        return Math.max(Math.abs(a), Math.abs(b));
    }

    public static float absMin(float a, float b) {
        return Math.min(Math.abs(a), Math.abs(b));
    }

    //Q
    public static float random() {
        return random.nextFloat();
    }

    public static float randomBetween(Random ran, float min, float max) {
        return ran.nextFloat(max - min + 1) + min;
    }

    public static float randomBetween(float min, float max) {
        return randomBetween(random, min, max);
    }

    public static double randomBetween(Random ran, double min, double max) {
        return ran.nextDouble(max - min + 1) + min;
    }

    public static double randomBetween(double min, double max) {
        return randomBetween(random, min, max);
    }

    public static int randomBetween(Random ran, int min, int max) {
        return ran.nextInt(max - min + 1) + min;
    }

    public static int randomBetween(int min, int max) {
        return randomBetween(random, min, max);
    }

    public static float random(@Message("value should be positive") float value) {
        return randomBetween(-value, value);
    }

    public static double random(@Message("value should be positive") double value) {
        return randomBetween(-value, value);
    }

    public static int random(@Message("value should be positive") int value) {
        return randomBetween(-value, value);
    }

    public static int aliveOrNull() {
        return random.nextBoolean() ? 1 : 0;
    }

    public static int trueOrFalse() {
        return random.nextBoolean() ? 1 : negate(1);
    }

    public static float trueOrFalse(float value) {
        return multiply(trueOrFalse(), value);
    }

    public static double trueOrFalse(double value) {
        return multiply(trueOrFalse(), value);
    }

    public static float divingPi(float pValue) {
        return pValue / PI;
    }

    public static float piDiving(float pValue) {
        return PI / pValue;
    }

    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public static Lerp lerp() {
        return new Lerp();
    }

    public static float lerp(float delta, float start, float end) {
        return new Lerp(delta, start, end).floatValue();
    }

    public static double lerp(double delta, double start, double end) {
        return new Lerp(delta, start, end).doubleValue();
    }

    public static class Lerp {
        private Float value;
        private Double doubleValue;
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

        public Float floatValue() {
            return value;
        }

        public Double doubleValue() {
            return doubleValue;
        }
    }
}
