
package org.nine_abyss.math;

import org.nine_abyss.annotation.Unused;

@Unused
public class Conditions {
    public static boolean isPositive(Object obj) {
        return obj instanceof Float f ? f > 0 : obj instanceof Double d ? d > 0 : obj instanceof Integer i ?
                i > 0 : obj instanceof Long l ? l > 0 : obj instanceof Short s ? s > 0 :
                obj instanceof Byte b ? b > 0 :
                obj instanceof Number number && number.floatValue() > 0;
    }
}
