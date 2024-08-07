package mz.mzlib.util.delegator;

@DelegatorArrayClass(Delegator.class)
public interface DelegatorArray<T extends Delegator> extends Delegator
{
	DelegatorArray<T> staticNewInstance(int length);
	
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
