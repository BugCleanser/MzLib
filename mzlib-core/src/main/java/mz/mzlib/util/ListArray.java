package mz.mzlib.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class ListArray<T> extends AbstractList<T> implements RandomAccess, java.io.Serializable
{
    static Map<Class<?>, MethodHandle> getters, setters;
    static
    {
        getters = new HashMap<>();
        setters = new HashMap<>();
        for(Class<?> i: CollectionUtil.toIterable(Stream.concat(ClassUtil.PRIMITIVES.stream(), Stream.of(Object.class))))
        {
            if(i == void.class)
                continue;
            Class<?> a = ClassUtil.arrayClass(i);
            getters.put(i, MethodHandles.arrayElementGetter(a).asType(MethodType.methodType(Object.class, Object.class, int.class)));
            setters.put(i, MethodHandles.arrayElementSetter(a).asType(MethodType.methodType(void.class, Object.class, int.class, Object.class)));
        }
    }
    
    final Object array;
    final int size;
    final MethodHandle getter;
    final MethodHandle setter;
    
    private ListArray(Object array)
    {
        this.array = array;
        this.size = Array.getLength(array);
        Class<?> erased = ClassUtil.erase(array.getClass().getComponentType());
        this.getter = getters.get(erased);
        this.setter = setters.get(erased);
    }
    
    public Object getArray()
    {
        return this.array;
    }
    
    @Override
    public int size()
    {
        return this.size;
    }
    
    @Override
    public Object[] toArray()
    {
        if(this.array instanceof Object[])
            return Arrays.copyOf((Object[]) this.array, this.size(), Object[].class);
        else
            return super.toArray();
    }
    
    @Override
    public T get(int index)
    {
        try
        {
            //noinspection unchecked
            return (T) (Object) this.getter.invokeExact(this.array, index);
        }
        catch(Throwable e)
        {
            throw new AssertionError(e);
        }
    }
    
    @Override
    public T set(int index, T element)
    {
        T last = this.get(index);
        try
        {
            this.setter.invokeExact(this.array, index, (Object) element);
        }
        catch(Throwable e)
        {
            throw new AssertionError(e);
        }
        return last;
    }
    
    public static ListArray<?> of(Object array)
    {
        return new ListArray<>(array);
    }
    
    /**
     * @see Arrays#asList(Object[])
     */
    @Deprecated
    public static <T> ListArray<T> of(T[] array)
    {
        return new ListArray<>(array);
    }
    
    public static ListArray<Byte> of(byte[] array)
    {
        return new ListArray<>(array);
    }
    public static ListArray<Short> of(short[] array)
    {
        return new ListArray<>(array);
    }
    public static ListArray<Integer> of(int[] array)
    {
        return new ListArray<>(array);
    }
    public static ListArray<Long> of(long[] array)
    {
        return new ListArray<>(array);
    }
    public static ListArray<Float> of(float[] array)
    {
        return new ListArray<>(array);
    }
    public static ListArray<Double> of(double[] array)
    {
        return new ListArray<>(array);
    }
    public static ListArray<Character> of(char[] array)
    {
        return new ListArray<>(array);
    }
    public static ListArray<Boolean> of(boolean[] array)
    {
        return new ListArray<>(array);
    }
}
