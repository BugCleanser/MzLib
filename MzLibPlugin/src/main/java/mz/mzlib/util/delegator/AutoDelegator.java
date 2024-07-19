package mz.mzlib.util.delegator;

import java.util.HashMap;
import java.util.Map;

public class AutoDelegator<T extends Delegator>
{
	public Map<Class<?>,Class<? extends T>> delegators=new HashMap<>();
	
	@SafeVarargs
	public AutoDelegator(Class<T> defaultDelegator,Class<? extends T> ...delegators)
	{
		this.delegators.put(Delegator.getDelegateClass(defaultDelegator),defaultDelegator);
		for(Class<? extends T> delegator:delegators)
			this.delegators.put(Delegator.getDelegateClass(delegator),delegator);
	}
	
	public T delegate(Object delegate)
	{
		Class<?> clazz=delegate.getClass();
		Class<? extends T> delegatorClass;
		while((delegatorClass=delegators.get(clazz))==null)
		{
			clazz=clazz.getSuperclass();
			if(clazz==null)
				throw new ClassCastException("No delegator found for class "+delegate.getClass().getName()+".");
		}
		return Delegator.create(delegatorClass,delegate);
	}
	
	public T cast(T delegator)
	{
		return this.delegate(delegator.getDelegate());
	}
}
