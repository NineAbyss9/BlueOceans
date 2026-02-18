
package org.NineAbyss9.util;

import javax.annotation.Nullable;

public class ValueHolder {
    public static final String EMPTY = "";
    public static final Integer ZERO = 0;

    /**Static type of {@linkplain Option#orElse(Object)}.
     *
     *@see Option#orElse(Object)
     *
     * @return {@code mayNull} if it is not null, otherwise returns {@code other}*/
    public static <R> R nullToOther(@Nullable R mayNull, R other) {
        return mayNull == null ? other : mayNull;
    }

    @SuppressWarnings("all")
    public static <T> T nullOf() {
        return null;
    }
}
