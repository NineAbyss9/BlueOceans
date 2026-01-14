
package org.nine_abyss.cache;

import org.nine_abyss.NineAbyssBase;

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

    public static void add(Object obj) {
        instance.caches.put(next(), obj);
    }

    public static void remove(Integer obj) {
        instance.caches.remove(obj);
    }
}
