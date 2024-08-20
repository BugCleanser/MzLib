package mz.mzlib.minecraft;

import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.MinecraftServer"))
public interface MinecraftServer extends WrapperObject, Instance
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static MinecraftServer create(Object wrapped)
    {
        return WrapperObject.create(MinecraftServer.class, wrapped);
    }

    MinecraftServer instance = RuntimeUtil.nul();

    @WrapMinecraftMethod(@VersionName(name = "getPlayerManager"))
    PlayerManager getPlayerManager();
}
