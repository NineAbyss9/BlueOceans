
package org.NineAbyss9.util.map;

import java.util.*;

public abstract class AbstractTwoValueMap<K, V, V1>
implements TowValueMap<K, V, V1> {
    protected AbstractTwoValueMap() {
    }

    public int size() {
        return entrySet().size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over {@code entrySet()} searching
     * for an entry with the specified value.  If such an entry is found,
     * {@code true} is returned.  If the iteration terminates without
     * finding such an entry, {@code false} is returned.  Note that this
     * implementation requires linear time in the size of the map.
     *
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean containsValue(Object value) {
        Iterator<Entry<K,V, V1>> i = entrySet().iterator();
        if (value == null) {
            while (i.hasNext()) {
                Entry<K,V, V1> e = i.next();
                if (e.getValue() == null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V, V1> e = i.next();
                if (value.equals(e.getValue()))
                    return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over {@code entrySet()} searching
     * for an entry with the specified key.  If such an entry is found,
     * {@code true} is returned.  If the iteration terminates without
     * finding such an entry, {@code false} is returned.  Note that this
     * implementation requires linear time in the size of the map; many
     * implementations will override this method.
     *
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean containsKey(Object key) {
        Iterator<Entry<K,V, V1>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V, V1> e = i.next();
                if (e.getKey()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V, V1> e = i.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over {@code entrySet()} searching
     * for an entry with the specified key.  If such an entry is found,
     * the entry's value is returned.  If the iteration terminates without
     * finding such an entry, {@code null} is returned.  Note that this
     * implementation requires linear time in the size of the map; many
     * implementations will override this method.
     *
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     */
    public V get(Object key) {
        Iterator<Entry<K,V, V1>> i = entrySet().iterator();
        if (key == null) {
            while (i.hasNext()) {
                Entry<K,V, V1> e = i.next();
                if (e.getKey() == null)
                    return e.getValue();
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V, V1> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue();
            }
        }
        return null;
    }

    public V put(K key, V value, V1 value1) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        this.entrySet().clear();
    }

    transient Set<K> keySet;
    transient Collection<V> values;
    transient Collection<V1> value1s;

    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new AbstractSet<>() {
                public Iterator<K> iterator() {
                    return new Iterator<>() {
                        private Iterator<Entry<K, V, V1>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractTwoValueMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractTwoValueMap.this.isEmpty();
                }

                public void clear() {
                    AbstractTwoValueMap.this.clear();
                }

                public boolean contains(Object k) {
                    return AbstractTwoValueMap.this.containsKey(k);
                }
            };
            keySet = ks;
        }
        return ks;
    }

    public Collection<V> values() {
        Collection<V> vals = values;
        if (vals == null) {
            vals = new AbstractCollection<>() {
                public Iterator<V> iterator() {
                    return new Iterator<>() {
                        private Iterator<Entry<K, V, V1>> entry = entrySet().iterator();

                        public boolean hasNext() {
                            return entry.hasNext();
                        }

                        public V next() {
                            return entry.next().getValue();
                        }

                        public void remove() {
                            entry.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractTwoValueMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractTwoValueMap.this.isEmpty();
                }

                public void clear() {
                    AbstractTwoValueMap.this.clear();
                }

                public boolean contains(Object o) {
                    return AbstractTwoValueMap.this.containsValue(o);
                }
            };
            values = vals;
        }
        return vals;
    }

    public abstract Set<Entry<K, V, V1>> entrySet();

    protected Object clone() throws CloneNotSupportedException {
        AbstractTwoValueMap<?, ?, ?> map = (AbstractTwoValueMap<?, ?, ?>)super.clone();
        map.keySet = null;
        map.values = null;
        map.value1s = null;
        return map;
    }

    public Map<K, V> kvMap() {
        HashMap<K, V> result = new HashMap<>();
        result.keySet().addAll(this.keySet());
        for (Entry<K, V, V1> entry : entrySet()) {
            result.entrySet().add(Map.entry(entry.getKey(), entry.getValue()));
        }
        return result;
    }
}
