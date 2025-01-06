package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

// TODO versioning
@WrapMinecraftClass(@VersionName(name="net.minecraft.server.network.ConnectedClientData"))
public interface ClientConnectionData extends WrapperObject
{
    @WrapperCreator
    static ClientConnectionData create(Object wrapped)
    {
        return WrapperObject.create(ClientConnectionData.class, wrapped);
    }
}
