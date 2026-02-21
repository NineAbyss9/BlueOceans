
package com.github.NineAbyss9.ix_api.util;

import com.google.common.annotations.VisibleForTesting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

/**A unmodifiable {@linkplain List}
 *
 * @see UnmodifiableSet
 *
 * @author Player_IX*/
public final class UnmodifiableList<T>
extends AbstractList<T>
implements List<T> {
    @VisibleForTesting
    final transient T[] array;

    @SafeVarargs
    private UnmodifiableList(T... objects) {
        array = objects;
    }

    @SafeVarargs
    public static <T> UnmodifiableList<T> of(T... objects) {
        return new UnmodifiableList<>(objects);
    }

    public T get(int index) {
        return array[index];
    }

    public int size() {
        return array.length;
    }

    public boolean remove(Object o) {
        return false;
    }

    @Nullable
    public T remove(int index) {
        return null;
    }

    public boolean removeAll(@Nonnull Collection<?> c) {
        return false;
    }

    @Nonnull
    public Object[] toArray() {
        return array;
    }
}
