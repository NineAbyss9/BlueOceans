
package org.nine_abyss.util;

public interface IXUtilUser {
    default IXUtil getIXUtil() {
        return new IXUtil(this);
    }
}
