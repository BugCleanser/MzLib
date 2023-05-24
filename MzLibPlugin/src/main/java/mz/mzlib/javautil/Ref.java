package mz.mzlib.javautil;

public interface Ref<T>
{
	T get();
	void set(T value);
}
