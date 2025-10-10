package mz.mzlib.util.compound;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.*;

import java.util.function.Function;

@WrapClass(IDelegator.class)
public interface Delegator extends WrapperObject
{
    WrapperFactory<Delegator> FACTORY = WrapperFactory.of(Delegator.class);
    @Deprecated
    @WrapperCreator
    static Delegator create(Object wrapped)
    {
        return WrapperObject.create(Delegator.class, wrapped);
    }
    
    static <T extends Delegator> T newInstance(WrapperFactory<T> factory, Object delegate)
    {
        try
        {
            T result = factory.create(Root.getUnsafe().allocateInstance(factory.getStatic().staticGetWrappedClass()));
            result.setDelegate(delegate);
            return result;
        }
        catch(InstantiationException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    @Deprecated
    static <T extends Delegator> T newInstance(Function<Object, T> creator, Object delegate)
    {
        return newInstance(new WrapperFactory<>(creator), delegate);
    }
    
    @WrapMethod("getDelegate")
    Object getDelegate();
    
    @WrapMethod("setDelegate")
    void setDelegate(Object delegate);
}
