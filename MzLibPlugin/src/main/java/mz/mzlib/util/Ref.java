package mz.mzlib.util;

public interface Ref<T>
{
    T get();

    void set(T value);
}
