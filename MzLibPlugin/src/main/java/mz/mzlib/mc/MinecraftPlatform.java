package mz.mzlib.mc;

import mz.mzlib.mappings.IMappings;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

import java.io.File;

public interface MinecraftPlatform extends Instance
{
    MinecraftPlatform instance=RuntimeUtil.nul();

    String getVersionString();
    int getVersion();
    default boolean inVersion(VersionName name)
    {
        return getVersion() >= name.begin() && getVersion() < name.end();
    }
    File getMzLibDataFolder();
    IMappings getMappingsP2Y(); // platform -> yarn
    IMappings getMappingsY2P(); // yarn -> platform
}
