
package org.NineAbyss9.value_holder;

public class KeyValueHolder<K, V> {
    private final K key;
    private final V value;

    public KeyValueHolder(K pKey, V pValue) {
        this.key = pKey;
        this.value = pValue;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
