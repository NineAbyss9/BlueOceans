
package org.nine_abyss.util.setter;

import java.util.Set;

public interface ISetter<E>
extends Set<E> {
    /**
     * You can use the implement like this: <blockquote><pre>
     * public void addAll(E... e) {
     *      java.util.Collections.addAll(this, e);
     * }</blockquote></pre>
     */
    void addAll(E... e);
}
