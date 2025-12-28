
package org.nine_abyss;

import org.nine_abyss.annotation.NotCheck;
import org.nine_abyss.block.TryBlock;
import org.nine_abyss.cache.Cache;
import org.nine_abyss.event.EventContainer;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

/**The base of {@code NineAbyss}
 *
 * @author NineAbyss*/
public class NineAbyssBase implements AutoCloseable
{
    public static final Unsafe UNSAFE = getUnsafe();
    public static EventContainer eventContainer;
    private NineAbyssBase() {
    }

    public static void setup() {
        new Cache();
        eventContainer = new EventContainer();
    }

    public void close() {
        Cache.clear();
        Cache.clearCache();
        eventContainer = null;
    }

    public static Cache cache() {
        return Cache.getInstance();
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public int hashCode() {
        return 9;
    }

    @NotCheck
    public String getVersion() {
        return "1.0.0";
    }

    public static void print(String msg) {
        System.out.print(msg);
    }

    public static String getAndPrint(String msg) {
        System.out.print(msg);
        return msg;
    }

    public static void error(String msg) {
        System.err.print(msg);
    }

    public static void error(Exception e) {
        System.err.print(e);
    }

    public static boolean run(Runnable work) {
        TryBlock tryBlock = new TryBlock(work);
        return tryBlock.run();
    }

    private static Unsafe getUnsafe() {
        if (UNSAFE != null) {
            return UNSAFE;
        } else {
            Unsafe instance = null;
            try {
                Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor();
                c.setAccessible(true);
                instance = c.newInstance();
            } catch (Exception e) {
                error(e);
            }
            return instance;
        }
    }
}
