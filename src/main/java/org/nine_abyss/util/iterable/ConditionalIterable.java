
package org.nine_abyss.util.iterable;

import org.nine_abyss.annotation.Unused;
import org.nine_abyss.util.function.ConditionalConsumer;

@Unused
public interface ConditionalIterable<E>
extends Iterable<E> {
    default void forEach(ConditionalConsumer<E> consumer) {
        for (E e : this) {
            consumer.accept(e1 -> true, e);
        }
    }
}
