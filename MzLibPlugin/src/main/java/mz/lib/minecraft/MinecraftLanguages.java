package mz.lib.minecraft;

import mz.lib.*;
import mz.lib.lang.*;
import mz.lib.minecraft.event.entity.player.*;
import mz.lib.minecraft.event.player.*;
import mz.lib.minecraft.task.*;
import mz.lib.module.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

public class MinecraftLanguages extends MzModule
{
	public static MinecraftLanguages instance=new MinecraftLanguages();
	
	private final Map<String,Map<String,String>> officialLanguages=new HashMap<>();
	
	@Override
	public void onLoad()
	{
		File legacy=new File(MzLib.instance.dataFolder,"lang");
		if(legacy.isDirectory())
		{
			reg(new DefaultLanguagesResources(legacy));
			FileUtil.delete(legacy);
		}
		try
		{
			reg(new DefaultLanguagesResources(new JarFile(MzLib.instance.jarFile),"lang"));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static Map<String,String> getLang(String lang)
	{
		if(instance.officialLanguages.containsKey(lang))
			return instance.officialLanguages.get(lang);
		Map<String,String> r=null;
		byte[] as=AssetsUtil.instance.getAsset("minecraft/lang/"+lang.toLowerCase()+".lang");
		if(as==null)
		{
			as=AssetsUtil.instance.getAsset("minecraft/lang/"+lang.toLowerCase()+".json");
			if(as!=null)
				r=DefaultLanguagesResources.loadJson(new String(as,StringUtil.UTF8));
			else
				r=new HashMap<>();
		}
		else
			r=DefaultLanguagesResources.loadProperties(new String(as,StringUtil.UTF8));
		instance.officialLanguages.put(lang,r);
		return r;
	}
	public static String get(String locale,String key)
	{
		if(Languages.instance.hasKey(locale,key))
			return Languages.instance.get(locale,key);
		Map<String,String> l=getLang(locale);
		if(l!=null)
			return l.get(key);
		return key;
	}
	public static boolean hasKey(String locale,String key)
	{
		if(Languages.instance.hasKey(locale,key))
			return true;
		Map<String,String> l=getLang(locale);
		return l!=null&&l.containsKey(key);
	}
	@EventHandler
	public void onPlayerLocaleChange(PlayerLocaleChangeEvent event)
	{
		reg(new SyncTask(()->
		{
			if(event.getPlayer().isOnline())
				event.getPlayer().updateInventory();
		}));
	}
}
