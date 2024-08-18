package mz.mzlib.mc;

import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftMethod;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass(@VersionName(name = "net.minecraft.server.MinecraftServer"))
public interface MinecraftServer extends Delegator, Instance
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static MinecraftServer create(Object delegate)
    {
        return Delegator.create(MinecraftServer.class, delegate);
    }

    MinecraftServer instance = RuntimeUtil.nul();

    @DelegatorMinecraftMethod(@VersionName(name = "getPlayerManager"))
    PlayerManager getPlayerManager();
}
