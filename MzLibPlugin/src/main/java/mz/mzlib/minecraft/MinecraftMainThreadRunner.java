package mz.mzlib.minecraft;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.async.AsyncFunctionRunner;

public interface MinecraftMainThreadRunner extends AsyncFunctionRunner, Instance
{
    MinecraftMainThreadRunner instance = RuntimeUtil.nul();
}
