package mz.mzlib.util;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CollectionUtil
{
    private CollectionUtil()
    {
    }

    public static MethodHandle synchronizedSet;

    public static <T> Stream<T> reverse(Stream<T> stream)
    {
        //noinspection ComparatorMethodParameterNotUsed
        return stream.sorted((prev, next) -> -1);
    }

    static
    {
        try
        {
            synchronizedSet = ClassUtil.findMethod(
                Collections.class, true, "synchronizedSet", Set.class, Set.class, Object.class);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public static <T> Set<T> synchronizedSet(Set<T> set, Object mutex)
    {
        try
        {
            return RuntimeUtil.cast((Set<?>) synchronizedSet.invokeExact((Set<?>) set, (Object) mutex));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    @SafeVarargs
    public static <T> List<T> newArrayList(T... elements)
    {
        return new ArrayList<>(Arrays.asList(elements));
    }

    public static <T> List<T> newArrayList(Iterable<T> elements)
    {
        return StreamSupport.stream(elements.spliterator(), false).collect(Collectors.toList());
    }

    public static <K, V> HashMap<K, V> newHashMap(K key, V value)
    {
        HashMap<K, V> result = new HashMap<>();
        result.put(key, value);
        return result;
    }

    @SafeVarargs
    public static <T, C extends Collection<T>> C addAll(C collection, T... elements)
    {
        collection.addAll(Arrays.asList(elements));
        return collection;
    }

    public static <T> Iterable<Ref<T>> each(List<T> list)
    {
        return () -> new Iterator<Ref<T>>()
        {
            final ListIterator<T> i = list.listIterator();
            @Override
            public boolean hasNext()
            {
                return this.i.hasNext();
            }
            @Override
            public void remove()
            {
                this.i.remove();
            }
            @Override
            public Ref<T> next()
            {
                int index = this.i.nextIndex();
                return new RefStrong<T>(this.i.next())
                {
                    @Override
                    public void set(T value)
                    {
                        if(i.previousIndex() != index)
                            throw new IllegalStateException();
                        super.set(value);
                        i.set(value);
                    }
                };
            }
        };
    }

    public static List<Object> replace(String str, String src, Object tar)
    {
        List<Object> result = new ArrayList<>();
        int i = 0, last = 0;
        while((i = str.indexOf(src, i)) != -1)
        {
            result.add(str.substring(last, i));
            result.add(tar);
            i += src.length();
            last = i;
        }
        result.add(str.substring(last));
        return result;
    }

    public static <T> List<List<T>> split(List<T> list, T separator)
    {
        List<List<T>> result = new ArrayList<>();
        List<T> current = new ArrayList<>();
        for(T i : list)
        {
            if(Objects.equals(i, separator))
            {
                result.add(current);
                current = new ArrayList<>();
            }
            else
            {
                current.add(i);
            }
        }
        result.add(current);
        return result;
    }

    public static Object[] toObjectArray(Object array)
    {
        if(array instanceof Object[])
            return (Object[]) array;
        Object[] result = (Object[]) Array.newInstance(
            ClassUtil.getWrapper(array.getClass().getComponentType()), Array.getLength(array));
        for(int i = 0; i < result.length; i++)
        {
            result[i] = Array.get(array, i);
        }
        return result;
    }
}
