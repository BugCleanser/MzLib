package mz.mzlib.util.wrapper;

@WrapArrayClass(WrapperObject.class)
public interface WrapperArray<T extends WrapperObject> extends WrapperObject
{
    WrapperArray<T> staticNewInstance(int length);

    T get(int index);

    default void set(int index, T value)
    {
        this.getWrapped()[index] = value.getWrapped();
    }

    @Override
    Object[] getWrapped();

    default int length()
    {
        return this.getWrapped().length;
    }
}
