package mz.mzlib.minecraft;

import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.ServerMetadata"))
public interface ServerMetadata extends WrapperObject
{
    @WrapperCreator
    static ServerMetadata create(Object wrapped)
    {
        return WrapperObject.create(ServerMetadata.class, wrapped);
    }
}
