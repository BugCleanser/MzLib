package mz.lib.wrapper;

import mz.lib.Ref;

public class WrappedRef<T extends WrappedObject> extends Ref<T>
{
	public volatile Class<T> wrapper;
	public volatile Ref<Object> rr;
	
	public WrappedRef(Class<T> wrapper,Ref<Object> rr)
	{
		super(null);
		this.wrapper=wrapper;
		this.rr=rr;
	}
	
	@Override
	public T get()
	{
		return WrappedObject.wrap(wrapper,rr.get());
	}
	
	@Override
	public void set(T obj)
	{
		rr.set(obj.getRaw());
	}
}
