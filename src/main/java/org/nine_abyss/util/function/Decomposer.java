
package org.nine_abyss.util.function;

@FunctionalInterface
public interface Decomposer<A, B, C> {
    void accept(A a, B b, C c);
}
