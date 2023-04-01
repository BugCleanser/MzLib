package mz.lib.minecraft.bukkit;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.*;
import org.bukkit.Bukkit;

public class ServerImpl extends Server
{
	public String MCProtocolVersion;
	public ServerImpl()
	{
		MCProtocolVersion=Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit.","");
		v17=TypeUtil.hasThrowable(()->Class.forName("net.minecraft.server."+MCProtocolVersion+".MinecraftServer"));
		v13=TypeUtil.hasThrowable(()->Class.forName("net.minecraft.server."+MCProtocolVersion+".ItemStack").getDeclaredField("damage"));
		MCVersion=Bukkit.getBukkitVersion().split("-")[0];
		version=Float.parseFloat(MCVersion.split("\\.",2)[1]);
	}
	
	public String convertClassName(String clName)
	{
		return clName.replace("nms.","net.minecraft.server."+((ServerImpl)Server.instance).MCProtocolVersion+".").replace("obc.","org.bukkit.craftbukkit."+((ServerImpl)Server.instance).MCProtocolVersion+".");
	}
}
