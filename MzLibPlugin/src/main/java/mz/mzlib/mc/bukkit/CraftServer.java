package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MinecraftServer;
import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.delegator.DelegatorOBCClass;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;
import mz.mzlib.util.delegator.DelegatorMethod;

@DelegatorOBCClass(@VersionName(name = "OBC.CraftServer"))
public interface CraftServer extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static CraftServer create(Object delegate)
    {
        return Delegator.create(CraftServer.class, delegate);
    }

    @DelegatorMethod("getServer")
    MinecraftServer getServer();
}
