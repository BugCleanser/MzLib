package mz.mzlib.util;

public class SimpleCloneable<T extends SimpleCloneable<T>> implements Cloneable
{
    @Override
    public T clone()
    {
        try
        {
            //noinspection unchecked
            return (T) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }
}
