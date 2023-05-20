package mz.mzlib.javautil;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CollectionUtil
{
	private CollectionUtil() {}
	
	public static MethodHandle synchronizedSet=ClassUtil.findMethod(Collections.class,true,"synchronizedSet",Set.class,Set.class,Object.class);
	
	public static <T> Set<T> synchronizedSet(Set<T> set,Object mutex)
	{
		try
		{
			return RuntimeUtil.forceCast((Set<?>)synchronizedSet.invokeExact((Set<?>)set,(Object)mutex));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
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
}
