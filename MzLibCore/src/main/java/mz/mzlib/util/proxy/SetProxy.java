package mz.mzlib.util.proxy;

import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.ModifyMonitor;

import java.util.Collection;
import java.util.Set;

public class SetProxy<T, U> extends CollectionProxy<T, U> implements Set<T>
{
    public SetProxy(Collection<U> delegate, InvertibleFunction<T, U> function, ModifyMonitor modifyMonitor)
    {
        super(delegate, function, modifyMonitor);
    }
}
