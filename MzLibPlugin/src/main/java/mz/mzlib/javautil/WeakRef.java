package mz.mzlib.javautil;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class WeakRef<T> implements Ref<T>
{
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
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Ref&&get()==((Ref<?>)obj).get();
	}
	@Override
	public int hashCode()
	{
		return Objects.hashCode(get());
	}
	@Override
	public String toString()
	{
		return Objects.toString(get());
	}
}
