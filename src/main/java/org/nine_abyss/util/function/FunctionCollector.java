
package org.nine_abyss.util.function;

import org.nine_abyss.util.IXUtil;

import java.util.function.*;

public class FunctionCollector {
    private FunctionCollector() {
        throw new AssertionError();
    }

    public static <T> Predicate<T> alwaysTrue() {
        return PredicateInstance.ALWAYS_TRUE.convert();
    }

    public static <T> Predicate<T> alwaysFalse() {
        return PredicateInstance.ALWAYS_FALSE.convert();
    }

    public static <T> Predicate<T> notnull() {
        return PredicateInstance.NOT_NULL.convert();
    }

    public static <T> Predicate<T> isNull() {
        return PredicateInstance.IS_NULL.convert();
    }

    public static BooleanSupplier positiveSupplier() {
        return BooleanSupplierInstance.TRUE;
    }

    public static BooleanSupplier negativeSupplier() {
        return BooleanSupplierInstance.FALSE;
    }

    public static <T> T get(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T> Supplier<T> supplier(T value) {
        return () -> value;
    }

    public static Runnable emptyAction() {
        return () -> {};
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

    private enum PredicateInstance implements Predicate<Object>, org.nine_abyss.util.IXUtilUser {
        ALWAYS_TRUE {
            public boolean test(Object t) {
                return true;
            }
        },
        ALWAYS_FALSE,
        NOT_NULL {
            public boolean test(Object t) {
                return t != null;
            }
        },
        IS_NULL {
            public boolean test(Object t) {
                return t == null;
            }
        };

        PredicateInstance() {}

        public boolean test(Object t) {
            return false;
        }

        <T> Predicate<T> convert() {
            return IXUtil.c.convert(this);
        }
    }
}
