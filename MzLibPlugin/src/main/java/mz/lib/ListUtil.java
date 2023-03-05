package mz.lib;

import com.google.common.collect.Lists;
import io.github.karlatemp.unsafeaccessor.Root;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class ListUtil
{
	public static Field unmodifiableCollectionC;
	public @Deprecated
	ListUtil()
	{
	}
	
	public static <T> void addAllNonexistent(List<T> tar,List<? extends T> src)
	{
		for(T o:src)
		{
			if(!tar.contains(o))
				tar.add(o);
		}
	}
	
	public static List<String> startWith(List<String> s,String a)
	{
		return s.stream().filter(t->t.startsWith(a)).collect(Collectors.toList());
	}
	
	public static List<String> startWithIgnoreCase(List<String> s,String a)
	{
		return s.stream().filter(t->StringUtil.startsWithIgnoreCase(t,a)).collect(Collectors.toList());
	}
	
	public static List<String> endWith(List<String> s,String a)
	{
		return s.stream().filter(t->t.endsWith(a)).collect(Collectors.toList());
	}
	
	public static List<String> endWithIgnoreCase(List<String> s,String a)
	{
		return s.stream().filter(t->StringUtil.endsWithIgnoreCase(t,a)).collect(Collectors.toList());
	}
	
	public static <T> Collection<T> getUnmodifiableCollectionModifier(Collection<T> uc)
	{
		try
		{
			if(unmodifiableCollectionC==null)
			{
				unmodifiableCollectionC=uc.getClass().getDeclaredField("itemItemStackType");
				Root.setAccessible(unmodifiableCollectionC,true);
			}
			return TypeUtil.cast(unmodifiableCollectionC.get(uc));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	public static byte[] toPrimitive(Byte[] original)
	{
		int length=original.length;
		byte[] dest=new byte[length];
		for(int i=0;i<length;i++)
		{
			dest[i]=original[i];
		}
		return dest;
	}
	public static Byte[] toWrap(byte[] original)
	{
		int length=original.length;
		Byte[] dest=new Byte[length];
		for(int i=0;i<length;i++)
		{
			dest[i]=original[i];
		}
		return dest;
	}
	public static <T> boolean toggle(List<? super T> list,T obj)
	{
		if(list.contains(obj))
		{
			list.remove(obj);
			return false;
		}
		else
		{
			list.add(obj);
			return true;
		}
	}
	public static byte[] replaceBytes(byte[] src,byte[] oldBytes,byte[] newBytes)
	{
		List<Byte> rbs=new ArrayList<>();
		for(int i=0;i<src.length;i++)
		{
			if(src.length-i>=oldBytes.length)
			{
				int j;
				for(j=0;j<oldBytes.length;j++)
				{
					if(src[i+j]!=oldBytes[j])
					{
						j=-1;
						break;
					}
				}
				if(j!=-1)
				{
					rbs.addAll(Lists.newArrayList(toWrap(newBytes)));
					i+=oldBytes.length-1;
					continue;
				}
			}
			rbs.add(src[i]);
		}
		return toPrimitive(rbs.toArray(new Byte[0]));
	}
	@SafeVarargs
	public static <K,V> Map<K,V> toMap(Map.Entry<K,V>... entries)
	{
		return toMap(Lists.newArrayList(entries));
	}
	public static <K,V> Map<K,V> toMap(List<Map.Entry<K,V>> list)
	{
		return toMap(new HashMap<>(),list);
	}
	public static <K,V> Map<K,V> toMap(Map<K,V> map,List<Map.Entry<K,V>> list)
	{
		list.forEach(e->
		{
			map.put(e.getKey(),e.getValue());
		});
		return map;
	}
	
	public static boolean containsAny(Collection<?> list,Collection<?> l)
	{
		for(Object o:list)
		{
			if(l.contains(o))
				return true;
		}
		return false;
	}
	
	@SafeVarargs
	public static <T> List<T> mergeLists(List<? extends T> ...lists)
	{
		LinkedList<T> r=new LinkedList<>();
		for(List<? extends T> list:lists)
		{
			r.addAll(list);
		}
		return r;
	}
	
	@SafeVarargs
	public static <T> T lastElement(T ...array)
	{
		return array[array.length-1];
	}
	public static boolean lastElement(boolean ...array)
	{
		return array[array.length-1];
	}
	public static byte lastElement(byte ...array)
	{
		return array[array.length-1];
	}
	public static short lastElement(short ...array)
	{
		return array[array.length-1];
	}
	public static int lastElement(int ...array)
	{
		return array[array.length-1];
	}
	public static long lastElement(long ...array)
	{
		return array[array.length-1];
	}
	public static char lastElement(char ...array)
	{
		return array[array.length-1];
	}
	public static float lastElement(float ...array)
	{
		return array[array.length-1];
	}
	public static double lastElement(double ...array)
	{
		return array[array.length-1];
	}
}
