package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.delegator.DelegatorBukkitClass;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorMethod;

@DelegatorBukkitClass(@VersionName(name="OBC.CraftServer"))
public interface ObcCraftServer extends Delegator
{
	@SuppressWarnings("deprecation")
	@DelegatorCreator
	static ObcCraftServer create(Object delegate)
	{
		return Delegator.create(ObcCraftServer.class,delegate);
	}
	
	@DelegatorMethod("getServer")
	MinecraftServerBukkit getServer();
}
