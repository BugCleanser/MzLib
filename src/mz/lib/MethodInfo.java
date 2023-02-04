package mz.lib;

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.Objects;

public class MethodInfo
{
	Class<?> clazz;
	String name;
	Class<?>[] argTypes;
	public MethodInfo(Class<?> clazz,String name,Class<?>... argTypes)
	{
		this.clazz=clazz;
		this.name=name;
		this.argTypes=argTypes;
	}
	public MethodInfo(Executable e)
	{
		this(e.getDeclaringClass(),new Function(e).getName(),e.getParameterTypes());
	}
	
	@Override
	public int hashCode()
	{
		return clazz.hashCode()^name.hashCode()^Arrays.hashCode(argTypes);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof MethodInfo))
			return false;
		MethodInfo m=(MethodInfo) obj;
		return m.clazz==this.clazz&&Objects.equals(name,m.name)&&Arrays.equals(argTypes,m.argTypes);
	}
}
