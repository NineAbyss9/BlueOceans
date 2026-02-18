
package org.NineAbyss9.util.map;

import java.util.Objects;

final class KeyValueHolder<K, V, V1>
implements TowValueMap.Entry<K, V, V1> {
    final K key;
    final V value;
    final V1 value1;

    KeyValueHolder(K k, V v, V1 v1) {
        key = Objects.requireNonNull(k);
        value = Objects.requireNonNull(v);
        value1 = Objects.requireNonNull(v1);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V1 getValue1() {
        return value1;
    }

    public V setValue(V v) {
        throw new UnsupportedOperationException();
    }

    public V1 setValue1(V1 v1) {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        return obj instanceof TowValueMap.Entry<?, ?, ?> entry
                && entry.getKey().equals(key)
                && entry.getValue().equals(value)
                && entry.getValue1().equals(value1);
    }

    public int hashCode() {
        return key.hashCode() ^ value.hashCode() ^ value1.hashCode();
    }

    public String toString() {
        return key + "=" + value + value1;
    }
}
