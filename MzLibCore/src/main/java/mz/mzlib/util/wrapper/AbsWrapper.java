package mz.mzlib.util.wrapper;

public abstract class AbsWrapper
{
    public Object wrapped;

    public AbsWrapper(Object wrapped)
    {
        this.wrapped = wrapped;
    }

    public Object getWrapped()
    {
        return wrapped;
    }

    public void setWrapped(Object wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public String toString()
    {
        return ((WrapperObject)this).toString0();
    }

    @Override
    public int hashCode()
    {
        return ((WrapperObject)this).hashCode0();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof WrapperObject && ((WrapperObject)this).equals0((WrapperObject) obj);
    }

    @Override
    @SuppressWarnings("all")
    public WrapperObject clone()
    {
        return ((WrapperObject)this).clone0();
    }
}
