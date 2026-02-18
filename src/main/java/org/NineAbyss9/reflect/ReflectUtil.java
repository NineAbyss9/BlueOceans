
package org.NineAbyss9.reflect;

import org.NineAbyss9.util.IXUtil;

import java.lang.reflect.*;

public class ReflectUtil {
    private ReflectUtil() {
    }

    public static String getSimpleName() {
        return ReflectUtil.class.getSimpleName();
    }

    public static Field getField(Class<?> clazz, String pName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(pName);
            field.setAccessible(true);
        } catch (Exception e) {
            IXUtil.l.warning("Find error in " + getSimpleName() + "e:" + e);
        }
        return field;
    }

    public static Method getMethod(Class<?> clazz, String pName) {
        try {
            Method method = clazz.getDeclaredMethod(pName);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            IXUtil.l.warning("Find error in " + getSimpleName() + "e:" + e);
        }
        return null;
    }

    public static void invoke(Class<?> clazz, String pName, Object target, Object... ps) {
        try {
            Method method = getMethod(clazz, pName);
            if (method != null) {
                method.invoke(target, ps);
            }
        } catch (Exception e) {
            IXUtil.l.warning("Find error in " + getSimpleName() + "e:" + e);
        }
    }
}
