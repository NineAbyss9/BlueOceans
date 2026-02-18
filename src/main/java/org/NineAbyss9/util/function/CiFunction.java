
package org.NineAbyss9.util.function;

/**@see java.util.function.BiFunction*/
@FunctionalInterface
public interface CiFunction<A, B, C, R> {
    R apply(A a, B b, C c);

    static <R, B, C> CiFunction<R, B, C, R> emptyA() {
        return (r, b, c) -> r;
    }

    static <A, R, C> CiFunction<A, R, C, R> emptyB() {
        return (a, b, c) -> b;
    }

    static <A, B, R> CiFunction<A, B, R, R> emptyC() {
        return (a, b, c) -> c;
    }
}
