
package org.NineAbyss9.util;

public final class Converter {
    Converter() {
    }

    @SuppressWarnings("unchecked")
    public <T> T convert(Object a) {
        return (T)a;
    }

    public <T> T convert(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }
}
