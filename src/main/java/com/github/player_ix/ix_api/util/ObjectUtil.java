
package com.github.player_ix.ix_api.util;

/**A util about objects
 * @see Object
 * @author Player_IX*/
public class ObjectUtil {
    public ObjectUtil() {
    }

    public static boolean nonnullEquals(Object a, Object b) {
        return a != null && a == b;
    }

    public static class UnsupportedTypeException
            extends IllegalArgumentException {
        public UnsupportedTypeException(String st) {
            super("Unsupported type for" + st);
        }

        public synchronized Throwable getCause() {
            return this;
        }

        public synchronized Throwable initCause(Throwable cause) {
            return this;
        }
    }

    public static class ZeroException
            extends RuntimeException {
        public ZeroException(String string) {
            super(string + "isZero");
        }

        public ZeroException() {
            super();
        }

        public synchronized Throwable getCause() {
            return this;
        }

        public synchronized Throwable initCause(Throwable cause) {
            return this;
        }
    }
}
