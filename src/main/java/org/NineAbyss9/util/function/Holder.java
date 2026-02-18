
package org.NineAbyss9.util.function;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public interface Holder<T> extends Supplier<T> {
    default T orElseThrow() {
        T result = get();
        if (result == null)
            throw new NoSuchElementException();
        return result;
    }

    default <X extends Throwable> T orElseThrow(X exc) throws X {
        T result = get();
        if (result == null)
            throw exc;
        return result;
    }

    default T orElseGet(Supplier<T> t) {
        T result;
        return (result = get()) == null ? t.get() : result;
    }
}
