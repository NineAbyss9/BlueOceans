
package org.nine_abyss.util.function;

import java.util.function.Predicate;

@FunctionalInterface
public interface ConditionalConsumer<T> {
    void accept(Predicate<T> predicate, T t);
}
