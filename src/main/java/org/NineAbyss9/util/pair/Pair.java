
package org.NineAbyss9.util.pair;

import org.NineAbyss9.util.Option;

import java.util.Map;
import java.util.Objects;

public abstract class Pair<L, R>
implements Map.Entry<L, R>, Comparable<Pair<L, R>>, java.io.Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 8947688449640458794L;
    Pair() {
    }

    public abstract L left();

    public abstract R right();

    public L getKey() {
        return left();
    }

    public R getValue() {
        return right();
    }

    public Pair<R, L> swap() {
        return of(right(), left());
    }

    public Pair<L, R> mutable() {
        return mutable(left(), right());
    }

    public Option<L> leftOption() {
        return Option.ofNullable(left());
    }

    public Option<R> rightOption() {
        return Option.ofNullable(right());
    }

    public abstract L setKey(L key);

    public int compareTo(Pair<L, R> o) {
        boolean leftEquals = Objects.equals(left(), o.left());
        boolean rightEquals = Objects.equals(right(), o.right());
        return leftEquals && rightEquals ? 0 : leftEquals ? 1 : -1;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Map.Entry<?,?> entry)
            return Objects.equals(entry.getKey(), getKey()) && Objects.equals(entry.getValue(), getValue());
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    public String toString() {
        return "Pair{" +
                "left:" + left() +
                ", right:" + right() +
                '}';
    }

    public static <L, R> Pair<L, R> mutable(L left, R right) {
        return MutablePair.of(left, right);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Pair<K, V> empty() {
        return new EmptyPair<>();
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return ImmutablePair.of(left, right);
    }

    public static <L, R> Pair<L, R> of(Map.Entry<L, R> entry) {
        return ImmutablePair.of(entry);
    }
}
