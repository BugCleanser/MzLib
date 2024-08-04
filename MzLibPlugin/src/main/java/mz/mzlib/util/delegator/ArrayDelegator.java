package mz.mzlib.util.delegator;

import mz.mzlib.util.RuntimeUtil;

@DelegatorArrayClass(Delegator.class)
public interface ArrayDelegator<T extends Delegator> extends Delegator
{
	ArrayDelegator<T> staticNewInstance(int length);
	static <T extends ArrayDelegator<?>> T newInstance(Class<T> type,int length)
	{
		return RuntimeUtil.cast(Delegator.createStatic(type).staticNewInstance(length));
	}
	
	T get(int index);
	default void set(int index,T value)
	{
		this.getDelegate()[index]=value.getDelegate();
	}
	
	@Override
	Object[] getDelegate();
	
	default int length()
	{
		return this.getDelegate().length;
	}
}
