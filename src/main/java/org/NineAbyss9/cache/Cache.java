
package org.NineAbyss9.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache {
    private final Map<Integer, Object> caches = new LinkedHashMap<>();
    private static int next;
    private static Cache instance;
    public Cache() {
        instance = this;
    }

    public static void clearCache() {
        instance = null;
    }

    public static void clear() {
        instance.caches.clear();
    }

    public static Cache getInstance() {
        return instance;
    }

    static int next() {
        return next++;
    }

    public Object get(int key) {
        return caches.get(key);
    }

    public static Object put(int key, Object value) {
        return instance.caches.put(key, value);
    }

    public static Object add(Object value) {
        return instance.caches.put(next(), value);
    }

    public static Object remove(Integer key) {
        return instance.caches.remove(key);
    }
}
