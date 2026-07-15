package mz.mzlib.util.wrapper;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
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
}
