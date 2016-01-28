package net.mediavrog.ruli;

/**
 * Created by maikvlcek on 1/27/16.
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
