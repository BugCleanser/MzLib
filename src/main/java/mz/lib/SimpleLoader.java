package mz.lib;

public class SimpleLoader extends ClassLoader
{
	public SimpleLoader()
	{
	}
	public SimpleLoader(ClassLoader parent)
	{
		super(parent);
	}
	public Class<?> loadClass(String name,byte[] bytes)
	{
		return super.defineClass(name,bytes,0,bytes.length);
	}
}
