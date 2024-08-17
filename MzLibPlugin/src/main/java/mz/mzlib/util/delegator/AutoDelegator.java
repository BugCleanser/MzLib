package mz.mzlib.util.delegator;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.util.RuntimeUtil;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

public class AutoDelegator<T extends Delegator>
{
    public Map<Class<?>, CallSite> delegatorConstructors = new HashMap<>();

    @SafeVarargs
    public AutoDelegator(Class<T> defaultDelegator, Class<? extends T>... delegators)
    {
        this.delegatorConstructors.put(Delegator.getDelegateClass(defaultDelegator), Delegator.getConstructorCallSite(Root.getTrusted(defaultDelegator), "create", MethodType.methodType(Delegator.class, Object.class), defaultDelegator));
        for (Class<? extends T> delegator : delegators)
        {
            this.delegatorConstructors.put(Delegator.getDelegateClass(delegator), Delegator.getConstructorCallSite(Root.getTrusted(delegator), "create", MethodType.methodType(Delegator.class, Object.class), delegator));
        }
    }

    public T delegate(Object delegate)
    {
        Class<?> clazz = delegate.getClass();
        CallSite constructor;
        while ((constructor = delegatorConstructors.get(clazz)) == null)
        {
            clazz = clazz.getSuperclass();
            if (clazz == null)
            {
                throw new ClassCastException("No delegator found for class " + delegate.getClass().getName() + ".");
            }
        }
        try
        {
            return RuntimeUtil.cast((Delegator) constructor.getTarget().invokeExact((Object) delegate));
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public T cast(T delegator)
    {
        return this.delegate(delegator.getDelegate());
    }
}
