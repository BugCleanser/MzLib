package mz.lib.lang;

import mz.lib.*;
import mz.lib.module.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class Languages extends MzModule implements IRegistrar<DefaultLanguagesResources>
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
	public Class<DefaultLanguagesResources> getType()
	{
		return DefaultLanguagesResources.class;
	}
	@Override
	public boolean register(MzModule module,DefaultLanguagesResources obj)
	{
		for(Map.Entry<String,Map<String,String>> e:obj.res.entrySet())
		{
			registerTimes.compute(e.getKey(),(k,v)->v!=null?v+1:1);
			MapUtil.putAllAbsent(map.computeIfAbsent(e.getKey(),k->new ConcurrentHashMap<>()),e.getValue());
		}
		return true;
	}
	@Override
	public void unregister(MzModule module,DefaultLanguagesResources obj)
	{
		for(Map.Entry<String,Map<String,String>> e:obj.res.entrySet())
		{
			registerTimes.computeIfPresent(e.getKey(),(k,v)->v-1);
			registerTimes.remove(e.getKey(),0);
		}
	}
	
	public File dataFile=new File(MzLib.instance.dataFolder,"languages.ser");
	public Saver saver=new Saver(()->
	{
		try(ObjectOutputStream oos=new ObjectOutputStream(Files.newOutputStream(dataFile.toPath())))
		{
			oos.writeObject(map);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	});
	@Override
	public void onLoad()
	{
		if(!dataFile.exists())
			return;
		try(ObjectInputStream ois=new ObjectInputStream(Files.newInputStream(dataFile.toPath())))
		{
			map=TypeUtil.cast(ois.readObject());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		reg(saver);
	}
}
