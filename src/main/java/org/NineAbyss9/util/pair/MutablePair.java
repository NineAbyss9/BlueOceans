
package org.NineAbyss9.util.pair;

public class MutablePair<L, R>
extends Pair<L, R> {
    @java.io.Serial
    private static final long serialVersionUID = 848409944736507155L;
    private L left;
    private R right;
    MutablePair() {
        left = null;
        right = null;
    }

    MutablePair(L l, R r) {
        left = l;
        right = r;
    }

    public L left() {
        return left;
    }

    public R right() {
        return right;
    }

    public L setKey(L key) {
        return left = key;
    }

    public R setValue(R value) {
        return right = value;
    }

    public static <L, R> MutablePair<L, R> of() {
        return new MutablePair<>();
    }

    public static <L, R> MutablePair<L, R> of(L left, R right) {
        return new MutablePair<>(left, right);
    }
}
