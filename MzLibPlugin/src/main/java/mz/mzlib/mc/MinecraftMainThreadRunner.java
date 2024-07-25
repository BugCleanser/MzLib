package mz.mzlib.mc;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.async.CoroutineRunner;

public interface MinecraftMainThreadRunner extends CoroutineRunner, Instance
{
	MinecraftMainThreadRunner instance=RuntimeUtil.nul();
}
