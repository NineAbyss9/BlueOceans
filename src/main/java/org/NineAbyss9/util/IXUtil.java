
package org.NineAbyss9.util;

import java.util.logging.Logger;

public final class IXUtil {
    final Object obj;
    public static final Converter c;
    public static final Logger l;
    public final Manager m;
    public static Nothing n;
    IXUtil(Object o) {
        obj = o;
        m = new Manager(o.getClass());
    }

    static {
        c = new Converter();
        l = Logger.getLogger("IXUtil");
        n = Nothing.getInstance();
    }
}
