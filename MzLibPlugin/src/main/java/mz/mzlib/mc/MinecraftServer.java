package mz.mzlib.mc;

import mz.mzlib.util.Instance;

public interface MinecraftServer extends Instance
{
	static MinecraftServer getInstance()
	{
		return Instance.get(MinecraftServer.class);
	}
	
	int getVersion();
	default boolean inVersion(VersionName name)
	{
		return getVersion()>=name.begin()&&getVersion()<name.end();
	}
}
