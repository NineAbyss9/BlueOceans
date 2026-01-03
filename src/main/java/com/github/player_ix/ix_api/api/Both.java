
package com.github.player_ix.ix_api.api;

/**@deprecated use {@linkplain java.util.function.BiConsumer}*/
@Deprecated(since = "1.0.9a")
@FunctionalInterface
public interface Both<A, B> {
    void accept(A var1, B var2);

    default Both<A, B> andThen(Both<? super A, ? super B> after) {
        return (a, b) -> {
            accept(a, b);
            after.accept(a, b);
        };
    }
}
