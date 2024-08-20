package mz.mzlib.util.wrapper;

import java.util.Objects;

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
        return Objects.toString(this.getWrapped());
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.getWrapped());
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof WrapperObject && Objects.equals(this.getWrapped(), ((WrapperObject) obj).getWrapped());
    }

    @Override
    @SuppressWarnings("all")
    public WrapperObject clone()
    {
        return clone0();
    }
}
