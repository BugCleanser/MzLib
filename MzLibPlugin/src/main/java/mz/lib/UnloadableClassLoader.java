package mz.lib;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnloadableClassLoader extends ClassLoader
{
	public Map<String,Class<?>> classes;
	
	public UnloadableClassLoader(ClassLoader parent)
	{
		super(parent);
		classes=new ConcurrentHashMap<>();
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException
	{
		Class<?> r=classes.get(name);
		if(r==null)
			throw new ClassNotFoundException(name);
		return r;
	}
	public class UnloadableClassLoaderPart extends ClassLoader
	{
		public UnloadableClassLoaderPart()
		{
			super(UnloadableClassLoader.this);
		}
		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException
		{
			return UnloadableClassLoader.this.findClass(name);
		}
	}
	public Class<?> loadClass(String name,byte[] data)
	{
		Class<?> r=ClassUtil.loadClass(name,data,new UnloadableClassLoaderPart());
		classes.put(name,r);
		return r;
	}
	public void unloadClass(String name)
	{
		classes.remove(name);
	}
	public void close()
	{
		classes.clear();
	}
}
