
package org.NineAbyss9.util;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**A container like {@linkplain Optional}, implements {@linkplain Supplier}, {@linkplain java.io.Serializable}.
 *
 * @see Optional
 *
 * @see Supplier
 *
 * @since 1.0.0
 *
 * @param <T> the type of the {@linkplain Option}
 *
 * @author NineAbyss*/
@SuppressWarnings("unused")
public final class Option<T>
implements Supplier<T>, java.io.Serializable {
    //SerialVersion
    @java.io.Serial
    private static final long serialVersionUID = 6561243608026846372L;
    /**The value of an {@linkplain Option}*/
    private final T value;

    /**
     * Constructs an instance with the described value.
     *
     * @param value the value to describe; it's the caller's responsibility to
     *        ensure the value is non-{@code null} unless creating the singleton
     *        instance returned by {@code empty()}.
     */
    Option(T value) {
        this.value = value;
    }

    /**Returns a new empty {@linkplain Option}
     *
     * @return a new empty {@linkplain Option}*/
    public static<T> Option<T> empty() {
        return new Option<>(null);
    }

    public static <T> Option<T> of(T value) {
        return new Option<>(Objects.requireNonNull(value));
    }

    public static <T> Option<T> ofNullable(T value) {
        return value == null ? new Option<>(null) : new Option<>(value);
    }

    /**{@inheritDoc}
     *
     *@return the {@linkplain Option#value} of an {@linkplain Option}*/
    public T get() {
        return this.value;
    }

    /**@see Optional#orElseGet(Supplier) */
    public T orElseGet(Supplier<T> supplier) {
        return value != null ? value : supplier.get();
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code Optional}
     * @throws NoSuchElementException if no value is present
     */
    public T orElseThrow() {
        if (isEmpty())
            throw new NoSuchElementException("No value present");
        return this.value;
    }

    /**@see Optional#orElseThrow(Supplier)*/
    public <X extends Throwable> T orElseThrow(Supplier<X> x) throws X {
        if (isPresent())
            return value;
        else
            throw x.get();
    }

    /**
     * If a value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no value is present.
     *        May be {@code null}.
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        if (this.isPresent())
            return value;
        else
            return other;
    }

    /**
     * If a value is present, returns the value, otherwise returns
     * {@code null}.
     *
     * @return the value, if present, otherwise {@code null}
     */
    public T getIf(boolean flag) {
        return ifOrElse(flag, null);
    }

    /**
     * If a {@code flag} is {@code true} returns the value, otherwise returns
     * {@code  other}.
     *
     * @param other the value to be returned, if no value is present.
     * May be {@code null}.
     *
     * @return the value, if present, otherwise {@code other}
     */
    public T ifOrElse(boolean flag, T other) {
        if (flag)
            return this.value;
        else
            return other;
    }

    public T ifOrElseThrow(boolean flag, Supplier<Throwable> exception) throws Throwable {
        if (flag)
            return this.value;
        else
            throw exception.get();
    }

    /**@param consumer will always accept the value, but if the {@linkplain Option#value} is {@code null},
     *  may throw {@linkplain NullPointerException}
     *
     * @throws NullPointerException if the {@linkplain #value} is {@code null}
     */
    public void run(Consumer<? super T> consumer)
    throws NullPointerException {
        consumer.accept(value);
    }

    /**@see Optional#ifPresent(Consumer)*/
    public void ifPresent(Consumer<? super T> consumer) {
        if (isPresent())
            consumer.accept(value);
    }

    /**@see Optional#ifPresentOrElse(Consumer, Runnable)*/
    public void ifPresentOrElse(Consumer<? super T> consumer, Runnable runnable) {
        if (isPresent())
            consumer.accept(value);
        else
            runnable.run();
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns an {@linkplain Option} describing the value, otherwise returns an
     * empty {@linkplain Option}.
     *
     * @param predicate the predicate to apply to a value, if present
     * @return an {@linkplain Option} describing the value of this
     *         {@linkplain Option}, if a value is present and the value matches the
     *         given predicate, otherwise an empty {@linkplain Option}
     * @throws NullPointerException if the predicate is {@code null}
     */
    public Option<T> filter(Predicate<? super T> predicate) {
        if (isEmpty())
            return this;
        else
            return predicate.test(value) ? this : Option.empty();
    }

    public Optional<T> asOptional() {
        if (this.isPresent())
            return Optional.of(value);
        else
            return Optional.empty();
    }

    public Supplier<T> asSupplier() {
        return () -> value;
    }

    /**@see Optional#stream()*/
    public Stream<T> stream() {
        if (isPresent())
            return Stream.of(value);
        else
            return Stream.empty();
    }

    /**@see Optional#equals(Object)*/
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else {
            if (obj instanceof Option<?> option)
                return Objects.equals(value, option.value);
            else {
                if (obj instanceof Optional<?> o)
                    return o.filter(object -> Objects.equals(object, value)).isPresent();
                return false;
            }
        }
    }

    /**@see Optional#hashCode() */
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**@see Optional#toString() */
    public String toString() {
        return value != null
                ? ("Option[" + value + "]")
                : "Option.empty";
    }

    public boolean isNaN() {
        if (value instanceof Double d)
            return d.isNaN();
        else if (value instanceof Float f)
            return f.isNaN();
        else
            return false;
    }

    /**@see Optional#isPresent() */
    public boolean isPresent() {
        return value != null;
    }

    /**@see Optional#isEmpty() */
    public boolean isEmpty() {
        return value == null;
    }
}
