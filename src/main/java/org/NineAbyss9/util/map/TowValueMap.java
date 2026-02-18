
package org.NineAbyss9.util.map;

import org.NineAbyss9.util.function.Decomposer;

import java.io.Serializable;
import java.util.*;

public interface TowValueMap<K, V, V1> {
    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    boolean containsV1(Object obj);

    V get(Object key);

    V1 getV1(Object key);

    V put(K key, V value, V1 value1);

    V remove(Object key);

    void putAll(TowValueMap<K, V, V1> map);

    void clear();

    Set<K> keySet();

    Collection<V> values();

    Collection<V1> valueCollection();

    Set<Entry<K, V, V1>> entrySet();

    Map<K, V> kvMap();

    Map<K, V1> kv1Map();

    interface Entry<K, V, V1> {
        K getKey();

        V getValue();

        V1 getValue1();

        V setValue(V v);

        V1 setValue1(V1 v1);

        boolean equals(Object obj);

        int hashCode();

        static <K, V, V1> Comparator<Entry<K, V, V1>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Entry<K, V, V1>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        static <K, V, V1> Comparator<Entry<K, V, V1>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Entry<K, V, V1>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }

        static <K, V, V1> Comparator<Entry<K, V, V1>> comparingByValue1(Comparator<? super V1> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Entry<K, V, V1>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getValue1(), c2.getValue1());
        }

        @SuppressWarnings("unchecked")
        static <K, V, V1> Entry<K, V, V1> copyOf(Entry<? extends K, ? extends V, ? extends V1> e) {
            Objects.requireNonNull(e);
            if (e instanceof KeyValueHolder) {
                return (Entry<K, V, V1>) e;
            } else {
                return TowValueMap.entry(e.getKey(), e.getValue(), e.getValue1());
            }
        }
    }

    boolean equals(Object obj);

    int hashCode();

    default V getOrDefault(K key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key)) ? v : defaultValue;
    }

    default V1 getOrDefaultV1(K key, V1 defaultValue) {
        V1 v;
        return (((v = getV1(key)) != null) || containsKey(key)) ? v : defaultValue;
    }

    default void forEach(Decomposer<? super K, ? super V, ? super V1> action) {
        Objects.requireNonNull(action);
        for (Entry<K, V, V1> entry : entrySet()) {
            K k;
            V v;
            V1 v1;
            try {
                k = entry.getKey();
                v = entry.getValue();
                v1 = entry.getValue1();
            } catch (IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v, v1);
        }
    }

    static <K, V, V1> Entry<K, V, V1> entry(K k, V v, V1 v1) {
        return new KeyValueHolder<>(k, v, v1);
    }
}
