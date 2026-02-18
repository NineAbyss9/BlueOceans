
package org.NineAbyss9.util.iterable;

import org.NineAbyss9.annotation.Unused;
import org.NineAbyss9.util.function.ConditionalConsumer;

@Unused
public interface ConditionalIterable<E>
extends Iterable<E> {
    default void forEach(ConditionalConsumer<E> consumer) {
        for (E e : this) {
            consumer.accept(e1 -> true, e);
        }
    }
}
