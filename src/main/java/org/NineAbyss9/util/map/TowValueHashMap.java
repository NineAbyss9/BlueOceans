
package org.NineAbyss9.util.map;

import java.util.*;

public class TowValueHashMap<K, V, V1>
extends AbstractTwoValueMap<K, V, V1> {
    public TowValueHashMap() {
    }

    transient Set<Entry<K, V, V1>> entrySet;

    transient int size;

    transient int modCount;

    int threshold;

    public int size() {
        return size;
    }

    public boolean containsV1(Object obj) {
        return false;
    }

    public V1 getV1(Object key) {
        return null;
    }

    public V remove(Object key) {
        return null;
    }

    public void putAll(TowValueMap<K, V, V1> map) {
    }

    public Collection<V1> valueCollection() {
        return List.of();
    }

    public Set<Entry<K, V, V1>> entrySet() {
        return Set.of();
    }

    public Map<K, V1> kv1Map() {
        return Map.of();
    }

    static class Node {

    }
}
