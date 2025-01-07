package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2002)
@WrapMinecraftClass(@VersionName(name="net.minecraft.server.network.ConnectedClientData"))
public interface ClientConnectionDataV2002 extends WrapperObject
{
    @WrapperCreator
    static ClientConnectionDataV2002 create(Object wrapped)
    {
        return WrapperObject.create(ClientConnectionDataV2002.class, wrapped);
    }
}
