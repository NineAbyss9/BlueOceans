
package org.nine_abyss.util.lister;

import java.util.*;
import java.util.function.Consumer;

public class SubLister<E>
extends LinkedList<E>
implements Lister<E> {
    public SubLister() {
        super();
    }

    public SubLister(Collection<? extends E> c) {
        super(c);
    }

    public boolean apply(int index, Consumer<? super E> action) {
        E element = this.get(index);
        action.accept(element);
        return true;
    }

    public boolean ifPresent(int index, Consumer<? super E> action) {
        E element = this.get(index);
        if (element != null) {
            action.accept(element);
            return true;
        }
        return false;
    }

    @SafeVarargs
    public static <E> SubLister<E> of(E... elements) {
        return new SubLister<>(Arrays.asList(elements));
    }

    public static <ELEMENT> SubLister<ELEMENT> copyOf(Iterable<? extends ELEMENT> elements) {
        if (elements instanceof Collection<? extends ELEMENT> c)
            return new SubLister<>(c);
        else {
            SubLister<ELEMENT> subLister = new SubLister<>();
            for (ELEMENT element : elements)
                subLister.add(element);
            return subLister;
        }
    }
}
