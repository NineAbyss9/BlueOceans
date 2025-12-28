
package org.nine_abyss.util;

public final class Converter {
    Converter() {
    }

    @SuppressWarnings("unchecked")
    public <T> T convert(Object a) {
        return (T)a;
    }
}
