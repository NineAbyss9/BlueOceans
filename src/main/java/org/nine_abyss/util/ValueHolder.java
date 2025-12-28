
package org.nine_abyss.util;

import org.nine_abyss.annotation.AlwaysNull;

public class ValueHolder {
    public static final String EMPTY = "";

    /**Static type of {@linkplain Option#orElse(Object)}.
     *
     *@see Option#orElse(Object)
     *
     * @return {@code mayNull} if it is not null, otherwise returns {@code other}*/
    public static <R> R nullToOther(R mayNull, R other) {
        return mayNull == null ? other : mayNull;
    }

    @AlwaysNull
    public static <T> T nullOf() {
        return null;
    }
}
