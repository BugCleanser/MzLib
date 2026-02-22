package mz.mzlib.util.compound;

import moe.karla.usf.unsafe.Unsafe;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

/**
 * @see DelegateField
 */
@Deprecated
@WrapClass(IDelegator.class)
public interface Delegator extends WrapperObject
{
    WrapperFactory<Delegator> FACTORY = WrapperFactory.of(Delegator.class);

    static <T extends Delegator> T newInstance(WrapperFactory<T> factory, Object delegate)
    {
        try
        {
            T result = factory.create(Unsafe.getUnsafe().allocateInstance(factory.getStatic().static$getWrappedClass()));
            result.setDelegate(delegate);
            return result;
        }
        catch(InstantiationException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    @WrapMethod("getDelegate")
    Object getDelegate();

    @WrapMethod("setDelegate")
    void setDelegate(Object delegate);
}
