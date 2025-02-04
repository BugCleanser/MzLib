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
        if(!(obj instanceof WrapperObject))
            return false;
        if(this.getWrapped()==null)
            return ((WrapperObject)obj).getWrapped()==null;
        return ((WrapperObject)this).equals0((WrapperObject) obj);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public WrapperObject clone()
    {
        return ((WrapperObject)this).clone0();
    }
}
