package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MinecraftServer;
import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.delegator.DelegatorBukkitClass;
import mz.mzlib.util.delegator.Delegator;
import org.bukkit.Bukkit;

import java.util.function.Supplier;

@DelegatorBukkitClass({@VersionName(end=1700,name="NMS.MinecraftServer"),@VersionName(begin=1700,name="net.minecraft.server.MinecraftServer")})
public interface MinecraftServerBukkit extends MinecraftServer, Delegator
{
	static MinecraftServerBukkit getInstance()
	{
		return ObcCraftServer.create(Bukkit.getServer()).getServer();
	}
	
	@Override
	default int getVersion()
	{
		return version;
	}
	
	@SuppressWarnings("all")
	int version=((Supplier<Integer>)()->
	{
		String[] versions=Bukkit.getBukkitVersion().split("-")[0].split("\\.",-1);
		return Integer.parseInt(versions[1])*100+(versions.length>2?Integer.parseInt(versions[2]):0);
	}).get();
}
