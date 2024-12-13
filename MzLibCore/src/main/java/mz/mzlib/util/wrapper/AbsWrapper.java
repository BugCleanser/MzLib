package mz.mzlib.util.wrapper;

public abstract class AbsWrapper implements WrapperObject
{
    public Object wrapped;

    public AbsWrapper(Object wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public Object getWrapped()
    {
        return wrapped;
    }

    @Override
    public void setWrapped(Object wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public String toString()
    {
        return this.toString0();
    }

    @Override
    public int hashCode()
    {
        return this.hashCode0();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof WrapperObject && this.equals0((WrapperObject) obj);
    }

    @Override
    @SuppressWarnings("all")
    public WrapperObject clone()
    {
        return clone0();
    }
}
