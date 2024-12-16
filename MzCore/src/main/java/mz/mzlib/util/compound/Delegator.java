package mz.mzlib.util.compound;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

@WrapClass(IDelegator.class)
public interface Delegator extends WrapperObject
{
    @WrapperCreator
    static Delegator create(Object wrapped)
    {
        return WrapperObject.create(Delegator.class, wrapped);
    }

    static <T extends Delegator> T newInstance(Function<Object,T> creator,Object delegate)
    {
        try
        {
            T result=creator.apply(Root.getUnsafe().allocateInstance(creator.apply(null).staticGetWrappedClass()));
            result.setDelegate(delegate);
            return result;
        }
        catch (InstantiationException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    @WrapMethod("getDelegate")
    Object getDelegate();
    @WrapMethod("setDelegate")
    void setDelegate(Object delegate);
}
