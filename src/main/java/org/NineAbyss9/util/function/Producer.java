
package org.NineAbyss9.util.function;

@FunctionalInterface
public interface Producer<A, B, C, D, E> {
    E produce(A a, B b, C c, D d);
}
