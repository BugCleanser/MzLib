package mz.lib.lang;

import com.google.common.reflect.*;
import com.google.gson.*;
import mz.lib.*;

import java.io.*;
import java.util.*;
import java.util.jar.*;

public class DefaultLanguagesResources
{
	public Map<String,Map<String,String>> res;
	
	public DefaultLanguagesResources()
	{
		this.res=new HashMap<>();
	}
	
	public DefaultLanguagesResources(File dir)
	{
		this();
		load(dir);
	}
	
	public DefaultLanguagesResources(JarFile jar,String dir)
	{
		this();
		load(jar,dir);
	}
	
	public void load(String locale,Map<String,String> map)
	{
		for(Map.Entry<String,String> e: map.entrySet())
			res.computeIfAbsent(e.getKey(),k->new HashMap<>()).put(locale,e.getValue());
	}
	
	public void load(File dir)
	{
		for(File f: Objects.requireNonNull(dir.listFiles()))
		{
			if(f.isDirectory())
				load(f);
			else
			{
				if(StringUtil.endsWithIgnoreCase(f.getName(),".lang"))
				{
					try(FileInputStream fis=new FileInputStream(f))
					{
						load(f.getName().substring(0,f.getName().length()-".lang".length()),loadProperties(new String(FileUtil.readInputStream(fis),StringUtil.UTF8)));
					}
					catch(Throwable e)
					{
						throw TypeUtil.throwException(e);
					}
				}
				else if(StringUtil.endsWithIgnoreCase(f.getName(),".json"))
				{
					try(FileInputStream fis=new FileInputStream(f))
					{
						load(f.getName().substring(0,f.getName().length()-".json".length()),loadJson(new String(FileUtil.readInputStream(fis),StringUtil.UTF8)));
					}
					catch(Throwable e)
					{
						throw TypeUtil.throwException(e);
					}
				}
			}
		}
	}
	
	public void load(JarFile jar,String dir)
	{
		try
		{
			Enumeration<JarEntry> entries=jar.entries();
			while(entries.hasMoreElements())
			{
				JarEntry entry=entries.nextElement();
				if(!entry.isDirectory() && entry.getName().startsWith(dir.endsWith("/") ? dir : dir+"/"))
				{
					String name=ListUtil.lastElement(entry.getName().split("/"));
					if(StringUtil.endsWithIgnoreCase(name,".lang"))
						load(name.substring(0,name.length()-".lang".length()),loadProperties(new String(FileUtil.readInputStream(jar.getInputStream(entry)),StringUtil.UTF8)));
					else if(StringUtil.endsWithIgnoreCase(name,".json"))
						load(name.substring(0,name.length()-".json".length()),loadJson(new String(FileUtil.readInputStream(jar.getInputStream(entry)),StringUtil.UTF8)));
				}
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	public static Map<String,String> loadProperties(String properties)
	{
		Properties p=new Properties();
		try
		{
			p.load(new StringReader(properties));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		return TypeUtil.cast(p);
	}
	
	@SuppressWarnings("all")
	public static Map<String,String> loadJson(String json)
	{
		return TypeUtil.cast(new Gson().fromJson(json,new TypeToken<Map<String,String>>()
		{
		}.getType()));
	}
}
