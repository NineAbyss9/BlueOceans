
package com.github.NineAbyss9.ix_api.util;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**A unmodifiable {@linkplain Set}
 * @see UnmodifiableList
 * @author Player_IX*/
public final class UnmodifiableSet<T>
extends HashSet<T>
implements Set<T> {
    final transient T[] array;
    @SafeVarargs
    private UnmodifiableSet(T... ts) {
        array = ts;
    }

    @Nonnull
    @SafeVarargs
    public static <T> UnmodifiableSet<T> of(T... ts) {
        return new UnmodifiableSet<>(ts);
    }

    @SafeVarargs
    public static <T> UnmodifiableSet<T> funny(T... ts) {
        Arrays.fill(ts, null);
        return new UnmodifiableSet<>(ts);
    }

    @Nonnull
    public Object[] toArray() {
        return array;
    }

    public int size() {
        return array.length;
    }

    public boolean add(T t) {
        return false;
    }

    public boolean removeAll(@Nonnull Collection<?> c) {
        return false;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean addAll(@Nonnull Collection<? extends T> c) {
        return false;
    }
}
