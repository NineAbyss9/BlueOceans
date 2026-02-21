
package com.github.NineAbyss9.ix_api.util;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.Random;

public final class Maths
extends Mth {
    public static final float CLOSER_PI = 3.141592653589793F;
    public static final float CLOSER_HALF_PI = CLOSER_PI / 2F;
    public static final float PI_DIVIDING_180 = CLOSER_PI / 180F;
    public static final double PI_DIVIDING_180_D = PI_DIVIDING_180;
    public static final Random random = new Random();
    public static int nextInt = random.nextInt();
    public static double nextDouble = random.nextDouble();
    public static float nextFloat = random.nextFloat();
    public static final byte ZERO_BYTE = (byte)0;
    public static final byte ONE_BYTE = (byte)1;
    public static final byte TWO_BYTE = (byte)2;

    public static int randomInt(int x) {
        if (x == 0) {
            return 0;
        }
        return (random.nextInt(x)) * Maths.trueOrFalse();
    }

    public static int randomInteger(int i) {
        return Maths.randomInteger(i, random);
    }

    public static int randomInteger(int i, @Nonnull Random source) {
        if (i == 0) {
            return 0;
        }
        return (i + source.nextInt(i)) * Maths.trueOrFalse();
    }

    public static float modelDegrees(float degree){
        return degree * CLOSER_PI /180.0F;
    }

    public static float trueOrFalse(float value) {
        return Maths.trueOrFalse() * value;
    }

    public static double trueOrFalse(double value) {
        return Maths.trueOrFalse() * value;
    }

    public static int trueOrFalse() {
        return Maths.trueOrFalse(random);
    }

    public static int trueOrFalse(@Nonnull Random source) {
        return source.nextBoolean() ? 1 : -1;
    }

    public static float smite(float f) {
        return 1F + (f * 0.5F);
    }

    public static int toTick(int tick) {
        return tick * 20;
    }

    public static float toTick(float tick) {
        return tick * 20F;
    }

    public static double toTick(double tick) {
        return tick * 20.0;
    }

    public static int minuteToTick(int minute) {
        return minute * 1200;
    }

    public static float minuteToTick(float minute) {
        return minute * 1200F;
    }

    @SuppressWarnings("deprecation")
    public static float randomBetween(float min, float max) {
        return randomBetween(RandomSource.createThreadSafe(), min, max);
    }

    public static double randomBetween(double min, double max) {
        return randomBetween((float)min, (float)max);
    }

    public static float healthLessThan(@Nonnull LivingEntity entity, float percent, float max) {
        return Math.min(entity.getMaxHealth() / percent, max);
    }
}
