
package org.NineAbyss9;

import org.NineAbyss9.annotation.NotCheck;
import org.NineAbyss9.block.TryBlock;
import org.NineAbyss9.cache.Cache;
import org.NineAbyss9.event.EventContainer;

/**The base of {@code NineAbyss}
 *
 * @author NineAbyss*/
public class NineAbyssBase //implements AutoCloseable
{
    public static EventContainer eventContainer;
    private NineAbyssBase() {
        throw new AssertionError();
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
}
