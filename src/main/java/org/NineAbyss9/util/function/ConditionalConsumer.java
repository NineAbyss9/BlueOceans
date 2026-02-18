
package org.NineAbyss9.util.function;

import java.util.function.Predicate;

@FunctionalInterface
public interface ConditionalConsumer<T> {
    void accept(Predicate<T> predicate, T t);
}
