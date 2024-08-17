package mz.mzlib.mc;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

public interface MinecraftServer extends Instance
{
    MinecraftServer instance = RuntimeUtil.nul();
}
