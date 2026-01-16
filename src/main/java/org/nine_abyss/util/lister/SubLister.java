
package org.nine_abyss.util.lister;

import java.util.*;
import java.util.function.Consumer;

public class SubLister<E>
extends LinkedList<E>
implements Lister<E> {
    @java.io.Serial
    private static final long serialVersionUID = 7474719356862439922L;
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

    public static <E> SubLister<E> copyOf(Iterable<? extends E> elements) {
        if (elements instanceof Collection<? extends E> c)
            return new SubLister<>(c);
        else {
            SubLister<E> subLister = new SubLister<>();
            for (E element : elements)
                subLister.add(element);
            return subLister;
        }
    }
}
