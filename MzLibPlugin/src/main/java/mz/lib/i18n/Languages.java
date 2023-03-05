package mz.lib.i18n;

import mz.lib.*;
import mz.lib.module.*;

import java.util.*;
import java.util.concurrent.*;

public class Languages implements IRegistrar<DefaultLangResources>
{
	public static Languages instance=new Languages();
	
	public Map<String,Map<String,String>> map=new ConcurrentHashMap<>();
	
	public boolean hasKey(String locale,String translationKey)
	{
		return map.containsKey(translationKey)&&map.get(translationKey).containsKey(locale);
	}
	public String get(String locale,String translationKey)
	{
		if(hasKey(locale,translationKey))
			return map.get(translationKey).get(locale);
		return translationKey;
	}
	
	public Map<String,Integer> registerTimes=new ConcurrentHashMap<>();
	@Override
	public Class<DefaultLangResources> getType()
	{
		return DefaultLangResources.class;
	}
	@Override
	public boolean register(MzModule module,DefaultLangResources obj)
	{
		for(Map.Entry<String,Map<String,String>> e:obj.res.entrySet())
		{
			registerTimes.compute(e.getKey(),(k,v)->v!=null?v+1:1);
			MapUtil.putAllAbsent(map.computeIfAbsent(e.getKey(),k->new ConcurrentHashMap<>()),e.getValue());
		}
		return true;
	}
	@Override
	public void unregister(MzModule module,DefaultLangResources obj)
	{
		for(Map.Entry<String,Map<String,String>> e:obj.res.entrySet())
		{
			registerTimes.compute(e.getKey(),(k,v)->v!=null ? v+1 : 1);
			registerTimes.remove(e.getKey(),0);
		}
	}
}
