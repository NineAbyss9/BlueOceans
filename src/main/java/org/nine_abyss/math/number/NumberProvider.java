
package org.nine_abyss.math.number;

public interface NumberProvider<T extends Number> {
    T sample();

    T min();

    T max();
}
