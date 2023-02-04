package mz.lib.minecraft.bukkit.wrapper;

import com.google.common.collect.Lists;
import mz.lib.*;
import mz.lib.minecraft.bukkit.VersionName;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;

public class BukkitWrapper
{
	/**
	 * MC1.13及以后
	 * 进行了扁平化
	 */
	public static final boolean v13;
	/**
	 * MC1.17及以后
	 * nms更改为官方映射表
	 */
	public static final boolean v17;
	public static String MCVersion;
	public static float version;
	public static String MCProtocolVersion=Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit.","");
	static
	{
		v17=TypeUtil.hasThrowable(()->Class.forName("net.minecraft.server."+MCProtocolVersion+".MinecraftServer"));
		v13=TypeUtil.hasThrowable(()->Class.forName("net.minecraft.server."+MCProtocolVersion+".ItemStack").getDeclaredField("damage"));
		MCVersion=Bukkit.getBukkitVersion().split("-")[0];
		version=Float.parseFloat(BukkitWrapper.MCVersion.split("\\.",2)[1]);
	}
	
	public static boolean inVersion(VersionName v)
	{
		return version>=v.minVer()&&version<v.maxVer();
	}
	public static boolean inVersion(WrappedBukkitConstructor v)
	{
		return version>=v.minVer()&&version<v.maxVer();
	}
	public static String[] inVersion(VersionName[] name)
	{
		List<String> r=new ArrayList<>();
		for(VersionName v:name)
		{
			if(inVersion(v))
				r.addAll(Lists.newArrayList(v.value()));
		}
		return r.toArray(new String[0]);
	}
	
	public static String cov(String clName)
	{
		return clName.replace("nms.","net.minecraft.server."+MCProtocolVersion+".").replace("obc.","org.bukkit.craftbukkit."+MCProtocolVersion+".");
	}
	public static String[] cov(String[] clName)
	{
		List<String> r=new ArrayList<>();
		for(String c:clName)
			r.add(cov(c));
		return r.toArray(new String[0]);
	}
}
