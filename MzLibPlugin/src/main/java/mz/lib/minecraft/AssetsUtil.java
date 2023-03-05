package mz.lib.minecraft;

import com.google.gson.*;
import mz.lib.*;

import java.io.*;
import java.net.*;

public class AssetsUtil
{
	public static AssetsUtil instance=new AssetsUtil();
	
	public JsonObject versionManifest;
	public JsonObject versionInfo;
	public JsonObject versionAssets;
	public JsonObject getVersionManifest()
	{
		if(versionManifest!=null)
			return versionManifest;
		File f=new File(MzLib.instance.dataFolder,"version_manifest.json");
		if(f.exists())
		{
			try(FileInputStream fis=new FileInputStream(f))
			{
				return versionManifest=new JsonParser().parse(new String(FileUtil.readInputStream(fis),StringUtil.UTF8)).getAsJsonObject();
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
		}
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
		return versionManifest=r;
	}
	public JsonObject getVersionInfo()
	{
		if(versionInfo!=null)
			return versionInfo;
		File f=new File(new File(MzLib.instance.dataFolder,"MCVersionInfo"),Server.instance.MCVersion+".json");
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
				throw TypeUtil.throwException(e);
			}
		}
		JsonObject[] rn=new JsonObject[]{null};
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
					throw TypeUtil.throwException(e);
				}
			}
		});
		if(rn[0]==null)
		{
			versionManifest=null;
			new File(MzLib.instance.dataFolder,"version_manifest.json").delete();
			return getVersionInfo();
		}
		return versionInfo=rn[0];
	}
	public JsonObject getVersionAssets()
	{
		if(versionAssets!=null)
			return versionAssets;
		File f=new File(new File(MzLib.instance.dataFolder,"MCVersionAssets"),Server.instance.MCVersion+".json");
		if(f.exists())
		{
			try(FileInputStream fis=new FileInputStream(f))
			{
				versionAssets=new JsonParser().parse(new String(FileUtil.readInputStream(fis),StringUtil.UTF8)).getAsJsonObject().get("objects").getAsJsonObject();
				return versionAssets;
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
		}
		JsonObject rn=null;
		try(InputStream dis=FileUtil.openConnectionCheckRedirects(new URL(getVersionInfo().get("assetIndex").getAsJsonObject().get("url").getAsString().replace("https://launchermeta.mojang.com","https://bmclapi2.bangbang93.com").replace("https://piston-meta.mojang.com","https://bmclapi2.bangbang93.com").replace("https://launcher.mojang.com","https://bmclapi2.bangbang93.com")).openConnection()))
		{
			byte[] bs=FileUtil.readInputStream(dis);
			f.getParentFile().mkdirs();
			f.createNewFile();
			try(FileOutputStream fos=new FileOutputStream(f))
			{
				fos.write(bs);
			}
			rn=new JsonParser().parse(new String(bs,StringUtil.UTF8)).getAsJsonObject().get("objects").getAsJsonObject();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		return versionAssets=rn;
	}
	public byte[] getAsset(String file)
	{
		File f=new File(new File(new File(MzLib.instance.dataFolder,"MCAssets"),Server.instance.MCVersion),file);
		if(f.exists())
		{
			try(FileInputStream fis=new FileInputStream(f))
			{
				return FileUtil.readInputStream(fis);
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
		}
		try
		{
			InputStream ras;
			byte[] r;
			switch(file)
			{
				case "minecraft/lang/en_us.lang":
					ras=AssetsUtil.class.getClassLoader().getResourceAsStream("assets/minecraft/lang/en_us.lang");
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
					ras=AssetsUtil.class.getClassLoader().getResourceAsStream("assets/minecraft/lang/en_us.json");
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
		if(getVersionAssets()==null)
			return null;
		JsonElement je=getVersionAssets().get(file);
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
}
