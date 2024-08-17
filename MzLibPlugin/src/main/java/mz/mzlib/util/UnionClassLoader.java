package mz.mzlib.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UnionClassLoader extends ClassLoader
{
    public static class MemberDelegator extends ClassLoader
    {
        public ClassLoader member;

        public MemberDelegator(UnionClassLoader parent)
        {
            super(parent);
        }

        @Override
        public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
        {
            try
            {
                DelegatorClassLoader delegator = DelegatorClassLoader.create(member);
                Class<?> result = delegator.findClass(name);
                if (resolve)
                {
                    delegator.resolveClass(result);
                }
                return result;
            }
            catch (ClassNotFoundException ignore)
            {
            }
            return super.loadClass(name, resolve);
        }
    }

    public UnionClassLoader()
    {
        super();
    }

    public UnionClassLoader(ClassLoader parent)
    {
        super(parent);
    }

    public Map<Float, Set<ClassLoader>> members = new ConcurrentHashMap<>();
    public Map<ClassLoader, Float> memberPriorities = new ConcurrentHashMap<>();

    public <E extends Throwable> ClassLoader addMember(ThrowableFunction<MemberDelegator, ClassLoader, E> memberAllocator, float priority) throws E
    {
        MemberDelegator memberDelegator = new MemberDelegator(this);
        memberDelegator.member = memberAllocator.apply(memberDelegator);
        memberPriorities.put(memberDelegator.member, priority);
        members.compute(priority, (aFloat, classLoaders) ->
        {
            if (classLoaders == null)
            {
                classLoaders = new HashSet<>();
            }
            classLoaders.add(memberDelegator.member);
            return classLoaders;
        });
        return memberDelegator.member;
    }

    public <E extends Throwable> ClassLoader addMember(ThrowableFunction<MemberDelegator, ClassLoader, E> memberAllocator) throws E
    {
        return addMember(memberAllocator, 0f);
    }

    public void removeMember(ClassLoader cl)
    {
        members.computeIfPresent(this.memberPriorities.remove(cl), (aFloat, classLoaders) ->
        {
            classLoaders.remove(cl);
            return classLoaders;
        });
    }

    public Set<String> loadingClasses = ConcurrentHashMap.newKeySet();

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
    {
        Class<?> result = null;
        try
        {
            result = super.loadClass(name, false);
        }
        catch (ClassNotFoundException ignore)
        {
        }
        if (result == null)
        {
            if (!loadingClasses.add(name))
            {
                return null;
            }
            try
            {
                for (Map.Entry<Float, Set<ClassLoader>> j : members.entrySet().stream().sorted((a, b) -> Float.compare(b.getKey(), a.getKey())).collect(Collectors.toList()))
                {
                    for (ClassLoader i : j.getValue())
                    {
                        try
                        {
                            result = i.loadClass(name);
                            if (result != null)
                            {
                                break;
                            }
                        }
                        catch (ClassNotFoundException ignore)
                        {
                        }
                    }
                    if (result != null)
                    {
                        break;
                    }
                }
            }
            finally
            {
                loadingClasses.remove(name);
            }
        }
        if (result == null)
        {
            throw new ClassNotFoundException(name);
        }
        if (resolve)
        {
            this.resolveClass(result);
        }
        return result;
    }

    public Set<String> gettingResources = ConcurrentHashMap.newKeySet();

    @Override
    public URL getResource(String name)
    {
        URL result = super.getResource(name);
        if (result == null)
        {
            if (!gettingResources.add(name))
            {
                return null;
            }
            try
            {
                for (Map.Entry<Float, Set<ClassLoader>> j : members.entrySet().stream().sorted((a, b) -> Float.compare(b.getKey(), a.getKey())).collect(Collectors.toList()))
                {
                    for (ClassLoader i : j.getValue())
                    {
                        result = i.getResource(name);
                        if (result != null)
                        {
                            break;
                        }
                    }
                    if (result != null)
                    {
                        break;
                    }
                }
            }
            finally
            {
                gettingResources.remove(name);
            }
        }
        return result;
    }

    @Override
    public InputStream getResourceAsStream(String name)
    {
        InputStream result = super.getResourceAsStream(name);
        if (result == null)
        {
            if (!gettingResources.add(name))
            {
                return null;
            }
            try
            {
                for (Map.Entry<Float, Set<ClassLoader>> j : members.entrySet().stream().sorted((a, b) -> Float.compare(b.getKey(), a.getKey())).collect(Collectors.toList()))
                {
                    for (ClassLoader i : j.getValue())
                    {
                        result = i.getResourceAsStream(name);
                        if (result != null)
                        {
                            break;
                        }
                    }
                    if (result != null)
                    {
                        break;
                    }
                }
            }
            finally
            {
                gettingResources.remove(name);
            }
        }
        return result;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException
    {
        List<URL> result = new ArrayList<>(Collections.list(super.getResources(name)));
        if (gettingResources.add(name))
        {
            try
            {
                for (Map.Entry<Float, Set<ClassLoader>> j : members.entrySet().stream().sorted((a, b) -> Float.compare(b.getKey(), a.getKey())).collect(Collectors.toList()))
                {
                    for (ClassLoader i : j.getValue())
                    {
                        result.addAll(Collections.list(i.getResources(name)));
                    }
                }
            }
            finally
            {
                gettingResources.remove(name);
            }
        }
        return Collections.enumeration(result);
    }
}
