
package org.nine_abyss.util;

public class ObjectUtil {
    private ObjectUtil() {
    }

    public static <T> T orElse(T t1, T t2) {
        return t1 == null ? t2 : t1;
    }
}
