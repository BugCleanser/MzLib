package mz.mzlib;

import mz.mzlib.javautil.Instance;
import mz.mzlib.module.MzModule;
import mz.mzlib.module.RegistrarRegistrar;

public class MzLib extends MzModule
{
	@Override
	public void onLoad()
	{
		register(Instance.InstanceRegistrar.instance);
		register(RegistrarRegistrar.instance);
	}
}
