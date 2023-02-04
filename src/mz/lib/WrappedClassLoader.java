package mz.lib;

import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@WrappedClass("java.lang.ClassLoader")
public interface WrappedClassLoader extends WrappedObject
{
	@WrappedMethod(value="findLoadedClass")
	<T> Class<T> findLoadedClass(String name);
	@WrappedMethod(value="findClass")
	<T> Class<T> findClass(String name);
	
	@Override
	ClassLoader getRaw();
}
