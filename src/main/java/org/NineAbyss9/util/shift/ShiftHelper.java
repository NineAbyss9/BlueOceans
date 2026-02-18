
package org.NineAbyss9.util.shift;

import org.NineAbyss9.math.AbyssMath;

public class ShiftHelper {
    /**This equal to <blockquote><pre>
     * pValue << pIndex
     * </pre></blockquote>
     *
     * @return pValue * 2<sup>n</sup>*/
    public static int leftShift(int pValue, int pIndex) {
        return pValue * AbyssMath.toThePowerOf(2, pIndex);
    }

    /**
     *
     * @return pValue * 2<sup>n</sup>*/
    public static int rightShift(int pValue, int pIndex) {
        return pValue / AbyssMath.toThePowerOf(2, pIndex);
    }
}
