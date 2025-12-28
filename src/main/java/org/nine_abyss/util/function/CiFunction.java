
package org.nine_abyss.util.function;

import org.nine_abyss.util.IXUtil;

@FunctionalInterface
public interface CiFunction<A, B, C, D> {
    D apply(A a, B b, C c);

    static <A, B, C, D> CiFunction<A, B, C, D> emptyA() {
        return (a, b, c) -> IXUtil.c.convert(a);
    }

    static <A, B, C, D> CiFunction<A, B, C, D> emptyB() {
        return (a, b, c) -> IXUtil.c.convert(b);
    }

    static <A, B, C, D> CiFunction<A, B, C, D> emptyC() {
        return (a, b, c) -> IXUtil.c.convert(c);
    }
}
