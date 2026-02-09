
package org.nine_abyss.math;

import org.nine_abyss.annotation.Unused;

@Unused
public class Conditions {
    public static boolean isPositive(Object obj) {
        return obj instanceof Float f ? f.floatValue() > 0 :
                obj instanceof Double d ? d.doubleValue() > 0 :
                obj instanceof Number number && number.floatValue() > 0;
    }
}
