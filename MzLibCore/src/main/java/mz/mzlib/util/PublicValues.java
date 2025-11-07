package mz.mzlib.util;

import java.lang.reflect.Method;

@Deprecated
public class PublicValues
{
    public static boolean isRoot;
    public static Object allocator;
    public static Method alloc;
    public static Method free;
    public static Method set;
    public static Method get;

    static
    {
        try
        {
            ClassLoader sysLoader = ClassLoader.getSystemClassLoader();
            isRoot = (PublicValues.class.getClassLoader() == sysLoader);
            if(isRoot)
            {
                allocator = new IndexAllocator<>();
            }
            else
            {
                Class<?> root;
                try
                {
                    root = Class.forName(PublicValues.class.getName(), false, sysLoader);
                }
                catch(ClassNotFoundException e)
                {
                    ClassUtil.defineClass(
                        sysLoader, IndexAllocator.class.getName(), ClassUtil.getByteCode(IndexAllocator.class));
                    root = ClassUtil.defineClass(
                        sysLoader, PublicValues.class.getName(), ClassUtil.getByteCode(PublicValues.class));
                }
                PublicValues.allocator = RuntimeUtil.cast(root.getDeclaredField("allocator").get(null));
            }
            PublicValues.alloc = PublicValues.allocator.getClass().getMethod("alloc");
            PublicValues.free = PublicValues.allocator.getClass().getMethod("free", int.class);
            PublicValues.set = PublicValues.allocator.getClass().getMethod("set", int.class, Object.class);
            PublicValues.get = PublicValues.allocator.getClass().getMethod("get", int.class);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public static int alloc()
    {
        if(PublicValues.isRoot)
        {
            return RuntimeUtil.<IndexAllocator<Object>>cast(PublicValues.allocator).alloc();
        }
        else
        {
            try
            {
                return (int) PublicValues.alloc.invoke(PublicValues.allocator);
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }

    public static void free(int index)
    {
        if(PublicValues.isRoot)
        {
            RuntimeUtil.<IndexAllocator<Object>>cast(PublicValues.allocator).free(index);
        }
        else
        {
            try
            {
                PublicValues.free.invoke(PublicValues.allocator, index);
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }

    public static void set(int index, Object obj)
    {
        if(PublicValues.isRoot)
        {
            RuntimeUtil.<IndexAllocator<Object>>cast(PublicValues.allocator).set(index, obj);
        }
        else
        {
            try
            {
                PublicValues.set.invoke(PublicValues.allocator, index, obj);
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }

    public static Object get(int index)
    {
        if(PublicValues.isRoot)
        {
            return RuntimeUtil.<IndexAllocator<Object>>cast(PublicValues.allocator).get(index);
        }
        else
        {
            try
            {
                return PublicValues.get.invoke(PublicValues.allocator, index);
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }
    }
}
