
package org.NineAbyss9.util.pair;

public final class ImmutablePair<L, R> extends Pair<L, R> {
    @java.io.Serial
    private static final long serialVersionUID = 4641014950638825776L;
    private final L left;
    private final R right;
    public ImmutablePair(final L pLeft, final R pRight) {
        left = pLeft;
        right = pRight;
    }

    public L left() {
        return left;
    }

    public R right() {
        return right;
    }

    public R setValue(R value) {
        throw new UnsupportedOperationException();
    }

    public L setKey(L key) {
        throw new UnsupportedOperationException();
    }

    public static <L, R> ImmutablePair<L, R> of(final L left, final R right) {
        return new ImmutablePair<>(left, right);
    }

    public static <L, R> ImmutablePair<L, R> of(java.util.Map.Entry<L, R> entry) {
        if (entry == null)
            return new ImmutablePair<>(null, null);
        return new ImmutablePair<>(entry.getKey(), entry.getValue());
    }
}
