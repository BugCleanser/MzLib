package mz.mzlib.util;

public interface Copyable<T extends Copyable<T>>
{
    T copy();
}
