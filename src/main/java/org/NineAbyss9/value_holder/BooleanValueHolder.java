
package org.NineAbyss9.value_holder;

public class BooleanValueHolder<T> {
    private final boolean bool;
    private final T value;
    public BooleanValueHolder(boolean pBool, T pValue) {
        this.bool = pBool;
        this.value = pValue;
    }

    public boolean getBool() {
        return bool;
    }

    public T getValue() {
        return value;
    }

    public T ifTrue() {
        if (bool)
            return value;
        else
            return null;
    }
}
