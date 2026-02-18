
package org.NineAbyss9.util.lister;

import java.util.Collection;
import java.util.NoSuchElementException;

public class ImmutableSubLister<E>
extends SubLister<E> {
    @java.io.Serial
    private static final long serialVersionUID = 5322653187890826517L;
    transient final E[] array;
    @java.lang.SafeVarargs
    ImmutableSubLister(E... elements) {
        super();
        array = elements;
    }

    @SuppressWarnings("unchecked")
    ImmutableSubLister(Collection<? extends E> c) {
        super(c);
        array = (E[])c.toArray();
    }

    public int size() {
        return array.length;
    }

    public E get(int index) {
        return this.array[index];
    }

    public E peek() {
        return array[0];
    }

    public E element() {
        return peek();
    }

    public E peekFirst() {
        return peek();
    }

    public E peekLast() {
        return array[size() - 1];
    }

    public E poll() {
        return peek();
    }

    public E pollFirst() {
        return peek();
    }

    public E pollLast() {
        return peekLast();
    }

    public E getFirst() {
        E e = array[0];
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    public E getLast() {
        E e = array[size() - 1];
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    public boolean add(E e) {
        return false;
    }

    public void addFirst(E e) {
    }

    public void addLast(E e) {
    }

    public void add(int index, E element) {
    }

    public E set(int index, E element) {
        return null;
    }

    public E remove(int index) {
        return null;
    }

    @java.lang.SafeVarargs
    public static <E> ImmutableSubLister<E> of(E... elements) {
        return new ImmutableSubLister<>(elements);
    }

    public static <E> ImmutableSubLister<E> copyOf(Iterable<? extends E> elements) {
        if (elements instanceof Collection<? extends E> c)
            return new ImmutableSubLister<>(c);
        else {
            ImmutableSubLister<E> subLister = new ImmutableSubLister<>();
            for (E element : elements)
                subLister.add(element);
            return subLister;
        }
    }
}
