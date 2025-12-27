package mz.mzlib.util;

public interface SelfType<Self extends SelfType<Self>>
{
    default Self self()
    {
        return RuntimeUtil.cast(this);
    }
}
