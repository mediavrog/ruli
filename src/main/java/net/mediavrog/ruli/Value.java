package net.mediavrog.ruli;

/**
 * Boxed value to support dynamic generation e.g. by querying a persistence layer.
 * Use {@link Value#as Value.as} shorthand to box a simple variable.
 */
public abstract class Value<T> {

    public static <T> Value<T> as(final T value) {
        return new Value<T>() {
            @Override
            public T get() {
                return value;
            }
        };
    }

    public abstract T get();

    public String describe() {
        return get().getClass().getSimpleName();
    }
}
