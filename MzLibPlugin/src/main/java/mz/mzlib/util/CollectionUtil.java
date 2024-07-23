package mz.mzlib.util;

import java.lang.invoke.MethodHandle;
import java.util.*;

public class CollectionUtil
{
	private CollectionUtil() {}
	
	public static MethodHandle synchronizedSet;
	static
	{
		try
		{
			synchronizedSet=ClassUtil.findMethod(Collections.class,true,"synchronizedSet",Set.class,Set.class,Object.class);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	
	public static <T> Set<T> synchronizedSet(Set<T> set,Object mutex)
	{
		try
		{
			return RuntimeUtil.cast((Set<?>)synchronizedSet.invokeExact((Set<?>)set,(Object)mutex));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	
	@SafeVarargs
	public static <T> List<T> newArrayList(T ...elements)
	{
		return new ArrayList<>(Arrays.asList(elements));
	}
	@SafeVarargs
	public static <T,C extends Collection<T>> C addAll(C collection,T ...elements)
	{
		collection.addAll(Arrays.asList(elements));
		return collection;
	}
	
	public static <T> List<List<T>> split(List<T> list,T separator)
	{
		List<List<T>> result=new ArrayList<>();
		List<T> current=new ArrayList<>();
		for(T i:list)
		{
			if(Objects.equals(i,separator))
			{
				result.add(current);
				current=new ArrayList<>();
			}
			else
				current.add(i);
		}
		result.add(current);
		return result;
	}
}
