
package org.nine_abyss.reflect;

import org.nine_abyss.util.IXUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
    private ReflectUtil() {
    }

    public static Field getField(Class<?> clazz, String pName) {
        try {
            Field field = clazz.getDeclaredField(pName);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            IXUtil.l.warning("Find error in " + ReflectUtil.class.getSimpleName() + "e:" + e);
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String pName) {
        try {
            Method method = clazz.getDeclaredMethod(pName);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            IXUtil.l.warning("Find error in " + ReflectUtil.class.getSimpleName() + "e:" + e);
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
            IXUtil.l.warning("Find error in " + ReflectUtil.class.getSimpleName() + "e:" + e);
        }
    }
}
