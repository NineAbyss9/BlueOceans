
package org.NineAbyss9.util.lister;

import org.NineAbyss9.value_holder.BooleanValueHolder;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public BooleanValueHolder<E> addValue(E pValue) {
        return new BooleanValueHolder<>(this.add(pValue), pValue);
    }

    public boolean accept(int index, Consumer<? super E> action) {
        action.accept(this.get(index));
        return true;
    }

    public <R> R apply(int index, Function<E, R> fun) {
        return fun.apply(this.get(index));
    }

    public boolean ifPresent(int index, Consumer<? super E> action) {
        E element = this.get(index);
        if (element != null) {
            action.accept(element);
            return true;
        }
        return false;
    }

    @java.lang.SafeVarargs
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
