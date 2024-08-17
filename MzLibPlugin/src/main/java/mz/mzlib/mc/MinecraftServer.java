package mz.mzlib.mc;

import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.delegator.Delegator;

@DelegatorMinecraftClass(@VersionName(name = "net.minecraft.server.MinecraftServer"))
public interface MinecraftServer extends Delegator, Instance
{
    MinecraftServer instance = RuntimeUtil.nul();
}
