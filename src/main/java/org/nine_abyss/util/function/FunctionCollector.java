
package org.nine_abyss.util.function;

import java.util.function.BooleanSupplier;

public class FunctionCollector {
    private FunctionCollector() {
        throw new AssertionError();
    }

    public static BooleanSupplier positiveSupplier() {
        return BooleanSupplierInstance.TRUE;
    }

    public static BooleanSupplier negativeSupplier() {
        return BooleanSupplierInstance.FALSE;
    }

    private enum BooleanSupplierInstance implements BooleanSupplier {
        TRUE(true),
        FALSE(false);
        final boolean b;
        BooleanSupplierInstance(boolean p) {
            b = p;
        }

        public boolean getAsBoolean() {
            return b;
        }
    }
}
