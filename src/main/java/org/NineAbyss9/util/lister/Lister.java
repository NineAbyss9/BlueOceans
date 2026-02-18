
package org.NineAbyss9.util.lister;

import org.NineAbyss9.math.MathSupport;
import org.NineAbyss9.util.*;
import org.NineAbyss9.value_holder.BooleanValueHolder;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.random.RandomGenerator;

public interface Lister<E> extends List<E>, Deque<E>, IXUtilUser {
    boolean accept(int index, Consumer<? super E> action);

    <R> R apply(int index, Function<E, R> fun);

    boolean ifPresent(int index, Consumer<? super E> action);

    E get(int index);

    E element();

    E peek();

    E poll();

    E peekLast();

    E peekFirst();

    boolean isEmpty();

    int size();

    BooleanValueHolder<E> addValue(E pValue);

    default void ifNotEmpty(Consumer<? super E> pAction) {
        if (!isEmpty()) {
            Iterator<E> iterator = iterator();
            while (iterator.hasNext())
                pAction.accept(iterator.next());
        }
    }

    boolean contains(Object obj);

    /**Gets a sample of a {@code SubLister}*/
    default E sample(RandomGenerator random) {
        return this.get(random.nextInt(size() - 1));
    }

    default E sample() {
        return sample(MathSupport.random);
    }

    default ImmutableSubLister<E> immutable() {
        return new ImmutableSubLister<>(this);
    }

    default SubLister<E> mutable() {
        return new SubLister<>(this);
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
