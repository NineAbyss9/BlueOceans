
package org.nine_abyss.util.function;

@FunctionalInterface
public interface Producer<A, B, C, D, E> {
    E produce(A a, B b, C c, D d);
}
