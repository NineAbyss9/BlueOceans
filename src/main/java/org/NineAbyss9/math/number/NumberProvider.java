
package org.NineAbyss9.math.number;

public interface NumberProvider<T extends Number> {
    T sample();

    T min();

    T max();
}
