
package org.NineAbyss9.util;

public interface IXUtilUser {
    default IXUtil getIXUtil() {
        return new IXUtil(this);
    }
}
