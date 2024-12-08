package mz.mzlib.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MergedClassLoader extends ClassLoader
{
    public Set<ClassLoader> classLoaders = ConcurrentHashMap.newKeySet();

    public Set<String> loadingClasses = new HashSet<>();

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
    {
        if (!loadingClasses.add(name))
        {
            return null;
        }
        try
        {
            for (ClassLoader i : classLoaders)
            {
                try
                {
                    Class<?> result = i.loadClass(name);
                    if (resolve)
                    {
                        this.resolveClass(result);
                    }
                    return result;
                }
                catch (ClassNotFoundException ignore)
                {
                }
            }
            throw new ClassNotFoundException(name);
        }
        finally
        {
            loadingClasses.remove(name);
        }
    }

    public void addClassLoader(ClassLoader classLoader)
    {
        classLoaders.add(classLoader);
    }
}
