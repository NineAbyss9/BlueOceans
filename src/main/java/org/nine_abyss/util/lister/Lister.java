
package org.nine_abyss.util.lister;

import org.nine_abyss.util.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.random.RandomGenerator;

public interface Lister<E> extends List<E>, Deque<E>, IXUtilUser {
    boolean apply(int index, Consumer<? super E> action);

    boolean ifPresent(int index, Consumer<? super E> action);

    E get(int index);

    E element();

    E peek();

    E poll();

    E peekLast();

    E peekFirst();

    int size();

    boolean contains(Object obj);

    /**Gets a sample of a {@code SubLister}*/
    default E sample(RandomGenerator random) {
        return this.get(random.nextInt(size() - 1));
    }

    default E sample() {
        return sample(new Random());
    }

    default Optional<E> checkedOptional(int index) {
        return Optional.ofNullable(this.get(index));
    }

    default Optional<E> withOptional(int index) {
        return Optional.of(this.get(index));
    }

    default Option<E> checkedOption(int index) {
        return Option.ofNullable(this.get(index));
    }

    default Option<E> withOption(int index) {
        return Option.of(this.get(index));
    }

    /**Please use the method with cation*/
    default <T> T convert(int index) {
        return IXUtil.c.convert(this.get(index));
    }
}
