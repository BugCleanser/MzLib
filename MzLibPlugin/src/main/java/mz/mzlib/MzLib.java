package mz.mzlib;

import mz.mzlib.module.MzModule;
import mz.mzlib.util.Instance;
import mz.mzlib.util.nothing.NothingClassRegistrar;

import java.util.HashSet;

public class MzLib extends MzModule
{
    public static MzLib instance = new MzLib();

    public void load()
    {
        if (this.isLoaded)
        {
            throw new IllegalStateException("Try to load the root module but it has been loaded.");
        }
        this.isLoaded = true;
        this.onLoad();
    }

    public void unload()
    {
        if (!this.isLoaded)
        {
            throw new IllegalStateException("Try to unload the root module but it's not loaded.");
        }
        for (MzModule i : new HashSet<>(this.submodules))
        {
            this.unregister(i);
        }
        for (Object i : new HashSet<>(this.registeredObjects.keySet()))
        {
            this.unregister(i);
        }
        this.isLoaded = false;
        this.onUnload();
    }

    @Override
    public void onLoad()
    {
        this.register(Instance.InstanceRegistrar.instance);

        this.register(NothingClassRegistrar.instance);
    }
}
