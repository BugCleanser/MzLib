package mz.mzlib.javautil;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class WeakRef<T> implements Ref<T>
{
	public int hash;
	public WeakReference<T> ref;
	
	public WeakRef(T value)
	{
		set(value);
	}
	
	@Override
	public T get()
	{
		return ref.get();
	}
	@Override
	public void set(T value)
	{
		ref=new WeakReference<>(value);
		hash=value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Ref&&get()==((Ref<?>)obj).get();
	}
	@Override
	public int hashCode()
	{
		if(get()==null)
			return hash;
		return get().hashCode();
	}
	@Override
	public String toString()
	{
		return Objects.toString(get());
	}
}
