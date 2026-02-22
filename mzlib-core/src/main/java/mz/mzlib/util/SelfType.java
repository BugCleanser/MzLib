package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface SelfType<Self extends SelfType<Self>>
{
    default Self self()
    {
        return RuntimeUtil.cast(this);
    }
}
