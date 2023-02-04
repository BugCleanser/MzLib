package mz.lib;

public class Ref<T> implements IRef<T>
{
	volatile T obj;
	public Ref(T obj)
	{
		set(obj);
	}
	public void set(T obj)
	{
		this.obj=obj;
	}
	public T get()
	{
		return obj;
	}
	@Override
	public int hashCode()
	{
		return get()==null?0:get().hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		new Object();
		if(!(obj instanceof Ref))
			return false;
		return ((Ref<?>) obj).get()==this.get();
	}
}
