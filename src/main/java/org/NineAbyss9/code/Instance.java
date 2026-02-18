
package org.NineAbyss9.code;

import org.NineAbyss9.util.IXUtil;

import java.util.Objects;
import java.util.function.Supplier;

public class Instance<T> implements Supplier<T> {
    private final T object;
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final Instance NULL = new Instance(null);
    public Instance(T pObj) {
        object = pObj;
    }

    public T get() {
        return object;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Instance<?> instance))
            return false;
        if (instance.object == null && this.object == null)
            return true;
        return Objects.equals(obj, object);
    }

    public int hashCode() {
        return this.object.hashCode();
    }

    public String toString() {
        if (object == null)
            return "Instance:NULL";
        return "Instance:" + this.object.getClass().getSimpleName();
    }

    public static <R> R nullOf() {
        return IXUtil.c.convert(NULL.get());
    }
}
