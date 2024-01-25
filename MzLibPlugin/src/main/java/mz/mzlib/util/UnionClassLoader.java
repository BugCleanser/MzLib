package mz.mzlib.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UnionClassLoader extends ClassLoader
{
	public UnionClassLoader()
	{
		super();
	}
	public UnionClassLoader(ClassLoader parent)
	{
		super(parent);
	}
	
	public Map<Float,Set<ClassLoader>> members=new ConcurrentHashMap<>();
	public Map<ClassLoader,Float> memberPriorities=new ConcurrentHashMap<>();
	public void addMember(ClassLoader cl,float priority)
	{
		memberPriorities.put(cl,priority);
		members.compute(priority,(aFloat,classLoaders) ->
		{
			if(classLoaders==null)
				classLoaders=new HashSet<>();
			classLoaders.add(cl);
			return classLoaders;
		});
	}
	public void addMember(ClassLoader cl)
	{
		addMember(cl,0f);
	}
	public void removeMember(ClassLoader cl)
	{
		members.computeIfPresent(this.memberPriorities.remove(cl),(aFloat,classLoaders) ->
		{
			classLoaders.remove(cl);
			return classLoaders;
		});
	}
	
	public Set<String> loadingClasses=new HashSet<>();
	@Override
	public synchronized Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException
	{
		Class<?> result=null;
		try
		{
			result=super.loadClass(name,false);
		}
		catch(ClassNotFoundException ignore)
		{
		}
		if(result==null)
		{
			if(loadingClasses.contains(name))
				return null;
			loadingClasses.add(name);
			try
			{
				for(Map.Entry<Float,Set<ClassLoader>> j: members.entrySet().stream().sorted((a,b)->Float.compare(b.getKey(),a.getKey())).collect(Collectors.toList()))
				{
					for(ClassLoader i: j.getValue())
					{
						try
						{
							result=i.loadClass(name);
							if(result!=null)
								break;
						}
						catch(ClassNotFoundException ignore)
						{
						}
					}
					if(result!=null)
						break;
				}
			}
			finally
			{
				loadingClasses.remove(name);
			}
		}
		if(result==null)
			throw new ClassNotFoundException(name);
		if(resolve)
			this.resolveClass(result);
		return result;
	}
}
