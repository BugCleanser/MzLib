package mz.lib.minecraft.bukkitlegacy;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import mz.lib.FileUtil;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkitlegacy.module.IRegistrar;
import mz.mzlib.*;
import mz.lib.minecraft.bukkit.nms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.obc.ObcChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

public class LangUtil extends AbsModule implements IRegistrar<LangUtil.LangFolder>
{
	public final Map<Plugin,LangFolder> autoUnregs=new ConcurrentHashMap<>();
	public Map<LangFolder,Map<String,Map<String,String>>> pluginLangs=new ConcurrentHashMap<>();
	private JsonObject versionManifest;
	private JsonObject versionInfo;
	private JsonObject versionAssets;
	private final Map<String,Map<String,String>> mcLangs=new HashMap<>();
	
	public static LangUtil instance=new LangUtil();
	public LangUtil()
	{
		super(MzLib.instance);
	}
	
	public static class LangFolder
	{
		public File file;
		public LangFolder(File file)
		{
			this.file=file;
		}
	}
	@Override
	public Class<LangFolder> getType()
	{
		return LangFolder.class;
	}
	@Override
	public void unregister(LangFolder obj)
	{
		instance.pluginLangs.remove(obj);
	}
	@EventHandler
	void onPluginDisable(PluginDisableEvent event)
	{
		LangUtil.unregLang(event.getPlugin());
	}
	
	@Override
	public void onEnable()
	{
		File lang=new File(MzLib.instance.getDataFolder(),"lang");
		try
		{
			FileUtil.exportDir(new JarFile(MzLib.instance.getFile()),"lang",lang,false);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		reg(new LangFolder(lang));
	}
	public JsonObject getVersionManifest()
	{
		if(versionManifest!=null)
			return versionManifest;
		File f=new File(MzLib.instance.getDataFolder(),"version_manifest.json");
		if(f.exists())
		{
			try(FileInputStream fis=new FileInputStream(f))
			{
				byte[] bs=new byte[(int) f.length()];
				fis.read(bs);
				versionManifest=new JsonParser().parse(new String(bs,StringUtil.UTF8)).getAsJsonObject();
				return versionManifest;
			}
			catch(Throwable e)
			{
				MzLib.instance.getLogger().warning("MC版本清单读取失败： "+e.getClass().getSimpleName()+" "+e.getMessage());
				e.printStackTrace();
			}
		}
		MzLib.instance.getLogger().info("正在下载MC版本清单");
		JsonObject r=null;
		try(InputStream dis=FileUtil.openConnectionCheckRedirects(new URL("https://bmclapi2.bangbang93.com/mc/game/version_manifest.json").openConnection()))
		{
			byte[] bs=FileUtil.readInputStream(dis);
			f.getParentFile().mkdirs();
			f.createNewFile();
			try(FileOutputStream fos=new FileOutputStream(f))
			{
				fos.write(bs);
			}
			r=new JsonParser().parse(new String(bs,StringUtil.UTF8)).getAsJsonObject();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		finally
		{
			if(r!=null)
			{
				versionManifest=r;
			}
			else
				MzLib.instance.getLogger().warning("下载MC版本清单失败");
		}
		return r;
	}
	public JsonObject getVersionInfo()
	{
		if(versionInfo!=null)
			return versionInfo;
		File f=new File(new File(MzLib.instance.getDataFolder(),"MCVersionInfo"),Server.instance.MCVersion+".json");
		if(f.exists())
		{
			try(FileInputStream fis=new FileInputStream(f))
			{
				byte[] bs=new byte[(int) f.length()];
				fis.read(bs);
				versionInfo=new JsonParser().parse(new String(bs,StringUtil.UTF8)).getAsJsonObject();
				return versionInfo;
			}
			catch(Throwable e)
			{
				MzLib.instance.getLogger().warning("当前MC版本信息读取失败： "+e.getClass().getSimpleName()+" "+e.getMessage());
				e.printStackTrace();
			}
		}
		MzLib.instance.getLogger().info("正在下载当前MC版本信息");
		JsonObject[] rn=new JsonObject[]{null};
		try
		{
			getVersionManifest().get("versions").getAsJsonArray().forEach(v->
			{
				if(v.getAsJsonObject().get("id").getAsString().equals(Server.instance.MCVersion))
				{
					try(InputStream dis=FileUtil.openConnectionCheckRedirects((new URL(v.getAsJsonObject().get("url").getAsString().replace("https://launchermeta.mojang.com","https://bmclapi2.bangbang93.com").replace("https://piston-meta.mojang.com","https://bmclapi2.bangbang93.com").replace("https://launcher.mojang.com","https://bmclapi2.bangbang93.com")).openConnection())))
					{
						byte[] bs=FileUtil.readInputStream(dis);
						f.getParentFile().mkdirs();
						f.createNewFile();
						try(FileOutputStream fos=new FileOutputStream(f))
						{
							fos.write(bs);
						}
						rn[0]=new JsonParser().parse(new String(bs,StringUtil.UTF8)).getAsJsonObject();
					}
					catch(Throwable e)
					{
						TypeUtil.throwException(e);
					}
				}
			});
			if(rn[0]==null)
			{
				versionManifest=null;
				new File(MzLib.instance.getDataFolder(),"version_manifest.json").delete();
				return getVersionInfo();
			}
		}
		finally
		{
			if(rn[0]!=null)
				versionInfo=rn[0];
			else
				MzLib.instance.getLogger().warning("当前MC版本信息下载失败");
		}
		return versionInfo;
	}
	public JsonObject getVersionAssets()
	{
		if(versionAssets!=null)
			return versionAssets;
		File f=new File(new File(MzLib.instance.getDataFolder(),"MCVersionAssets"),Server.instance.MCVersion+".json");
		if(f.exists())
		{
			try(FileInputStream fis=new FileInputStream(f))
			{
				byte[] bs=new byte[(int) f.length()];
				fis.read(bs);
				versionAssets=new JsonParser().parse(new String(bs,StringUtil.UTF8)).getAsJsonObject().get("objects").getAsJsonObject();
				return versionAssets;
			}
			catch(Throwable e)
			{
				MzLib.instance.getLogger().warning("当前MC版本资源列表读取失败： "+e.getClass().getSimpleName()+" "+e.getMessage());
				e.printStackTrace();
			}
		}
		MzLib.instance.getLogger().info("正在下载当前MC版本资源列表");
		JsonObject[] rn=new JsonObject[]{null};
		try(InputStream dis=FileUtil.openConnectionCheckRedirects(new URL(getVersionInfo().get("assetIndex").getAsJsonObject().get("url").getAsString().replace("https://launchermeta.mojang.com","https://bmclapi2.bangbang93.com").replace("https://piston-meta.mojang.com","https://bmclapi2.bangbang93.com").replace("https://launcher.mojang.com","https://bmclapi2.bangbang93.com")).openConnection()))
		{
			byte[] bs=FileUtil.readInputStream(dis);
			f.getParentFile().mkdirs();
			f.createNewFile();
			try(FileOutputStream fos=new FileOutputStream(f))
			{
				fos.write(bs);
			}
			rn[0]=new JsonParser().parse(new String(bs,StringUtil.UTF8)).getAsJsonObject().get("objects").getAsJsonObject();
		}
		catch(Throwable e)
		{
			TypeUtil.throwException(e);
		}
		finally
		{
			if(rn[0]!=null)
				versionAssets=rn[0];
			else
				MzLib.instance.getLogger().warning("当前MC版本资源列表下载失败");
		}
		return versionAssets;
	}
	public static byte[] getAsset(String file)
	{
		File f=new File(new File(new File(MzLib.instance.getDataFolder(),"MCAssets"),Server.instance.MCVersion),file);
		if(f.exists())
		{
			try(FileInputStream fis=new FileInputStream(f))
			{
				return FileUtil.readInputStream(fis);
			}
			catch(Throwable e)
			{
				MzLib.instance.getLogger().warning("MC资源("+file+")读取失败： "+e.getClass().getSimpleName()+" "+e.getMessage());
				e.printStackTrace();
			}
		}
		try
		{
			InputStream ras;
			byte[] r;
			switch(file)
			{
				case "minecraft/lang/en_us.lang":
					ras=Bukkit.class.getClassLoader().getResourceAsStream("assets/minecraft/lang/en_us.lang");
					if(ras==null)
						return null;
					r=FileUtil.readInputStream(ras);
					f.getParentFile().mkdirs();
					f.createNewFile();
					try(FileOutputStream fos=new FileOutputStream(f))
					{
						fos.write(r);
					}
					return r;
				case "minecraft/lang/en_us.json":
					ras=Bukkit.class.getClassLoader().getResourceAsStream("assets/minecraft/lang/en_us.json");
					if(ras==null)
						return null;
					r=FileUtil.readInputStream(ras);
					f.getParentFile().mkdirs();
					f.createNewFile();
					try(FileOutputStream fos=new FileOutputStream(f))
					{
						fos.write(r);
					}
					return r;
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		if(instance.getVersionAssets()==null)
			return null;
		JsonElement je=instance.getVersionAssets().get(file);
		if(je==null)
			return null;
		JsonObject ass=je.getAsJsonObject();
		String hash=ass.get("hash").getAsString();
		try
		{
			byte[] bs=FileUtil.readInputStream(new DataInputStream(FileUtil.openConnectionCheckRedirects(new URL("http://bmclapi2.bangbang93.com/assets/"+hash.substring(0,2)+"/"+hash).openConnection())));
			f.getParentFile().mkdirs();
			f.createNewFile();
			try(FileOutputStream fos=new FileOutputStream(f))
			{
				fos.write(bs);
			}
			return bs;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static Map<String,String> loadLangProperties(String properties)
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
	@SuppressWarnings("serial")
	public static Map<String,String> loadLangJson(String json)
	{
		return TypeUtil.cast(new Gson().fromJson(json,new TypeToken<Map<String,String>>()
		{
		}.getType()));
	}
	public static Map<String,String> getLang(String lang)
	{
		if(instance.mcLangs.containsKey(lang))
			return instance.mcLangs.get(lang);
		Map<String,String> r=null;
		byte[] as=getAsset("minecraft/lang/"+lang.toLowerCase()+".lang");
		if(as==null)
		{
			as=getAsset("minecraft/lang/"+lang.toLowerCase()+".json");
			if(as!=null)
				r=loadLangJson(new String(as,StringUtil.UTF8));
			else
				r=new HashMap<>();
		}
		else
		{
			r=loadLangProperties(new String(as,StringUtil.UTF8));
		}
		instance.mcLangs.put(lang,r);
		return r;
	}
	public static void regLang(LangFolder dir,String locale,Map<String,String> lang)
	{
		if(!instance.pluginLangs.containsKey(dir))
		{
			instance.pluginLangs.put(dir,new HashMap<>());
		}
		instance.pluginLangs.get(dir).put(locale,lang);
	}
	public static void unregLang(Plugin plugin)
	{
		if(!instance.autoUnregs.containsKey(plugin))
			return;
		instance.unregister(instance.autoUnregs.get(plugin));
		instance.autoUnregs.remove(plugin);
	}
	public static String getTranslated(String locale,String key)
	{
		if(key==null)
			return "null";
		for(Map<String,Map<String,String>> v: instance.pluginLangs.values())
		{
			if(v.containsKey(locale))
			{
				Map<String,String> l=v.get(locale);
				if(l.containsKey(key))
					return l.get(key);
			}
		}
		Map<String,String> l=getLang(locale);
		if(l!=null)
		{
			String r=l.get(key);
			if(r!=null)
				return r;
		}
		for(Map<String,Map<String,String>> v: instance.pluginLangs.values())
		{
			if(v.containsKey("default"))
			{
				if(v.get("default").containsKey(key))
					return v.get("default").get(key);
			}
		}
		return key;
	}
	public static String getTranslated(CommandSender player,String key)
	{
		return getTranslated(getLang(player),key);
	}
	public static boolean hasKey(String locale,String key)
	{
		for(Map<String,Map<String,String>> v: instance.pluginLangs.values())
		{
			if(v.containsKey(locale))
			{
				if(v.get(locale).containsKey(key))
					return true;
			}
		}
		Map<String,String> l=getLang(locale);
		return l!=null&&l.containsKey(key);
	}
	public static boolean hasKey(CommandSender player,String key)
	{
		return hasKey(getLang(player),key);
	}
	
	public static void translated0(JsonObject component,String locale)
	{
		if(component.has("translate"))
		{
			component.add("text",new JsonPrimitive(getTranslated(locale,component.get("translate").getAsString())));
		}
		if(component.has("extra"))
		{
			JsonArray n=new JsonArray();
			component.get("extra").getAsJsonArray().forEach(e->
			{
				JsonObject o=e.getAsJsonObject();
				translated0(o,locale);
				n.add(o);
			});
			component.add("extra",n);
		}
	}
	public static NmsIChatBaseComponent translated(NmsIChatBaseComponent component,String locale)
	{
		JsonObject json=new JsonParser().parse(ObcChatMessage.toJson(component)).getAsJsonObject();
		translated0(json,locale);
		return NmsIChatBaseComponent.NmsChatSerializer.jsonToComponent(json.toString());
	}
	public static void loadLangs(Plugin plugin,File dir)
	{
		LangFolder lf=new LangFolder(dir);
		instance.register(lf);
		instance.autoUnregs.put(plugin,lf);
	}
	@Override
	public boolean register(LangFolder dir)
	{
		for(File f: dir.file.listFiles())
		{
			if(f.isDirectory())
				register(new LangFolder(f));
			else
			{
				if(StringUtil.endsWithIgnoreCase(f.getName(),".lang"))
				{
					try(FileInputStream fis=new FileInputStream(f))
					{
						regLang(dir,f.getName().substring(0,f.getName().length()-".lang".length()),loadLangProperties(new String(FileUtil.readInputStream(fis),StringUtil.UTF8)));
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
						regLang(dir,f.getName().substring(0,f.getName().length()-".json".length()),loadLangJson(new String(FileUtil.readInputStream(fis),StringUtil.UTF8)));
					}
					catch(Throwable e)
					{
						throw TypeUtil.throwException(e);
					}
				}
			}
		}
		return true;
	}
	public static String getLang(Player player)
	{
		if(player!=null)
			return player.getLocale();
		return MzLib.instance.getConfig().getString("defaultLang","en_us");
	}
	public static String getLang(CommandSender sender)
	{
		return getLang((Player) (sender instanceof Player?sender:null));
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	void onPlayerLocaleChange(PlayerLocaleChangeEvent event)
	{
		Bukkit.getScheduler().runTask(MzLib.instance,()->
		{
			if(event.getPlayer().isOnline())
				event.getPlayer().updateInventory();
		});
	}
}
