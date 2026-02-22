package mz.mzlib.minecraft;

import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.ServerMetadata"))
public interface ServerMetadata extends WrapperObject
{
    WrapperFactory<ServerMetadata> FACTORY = WrapperFactory.of(ServerMetadata.class);
    }

