
package org.NineAbyss9.util.pair;

final class EmptyPair<L, R> extends Pair<L, R> {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    public L left() {
        return null;
    }

    public R right() {
        return null;
    }

    public R setValue(R value) {
        return null;
    }

    public L setKey(L key) {
        return null;
    }
}
