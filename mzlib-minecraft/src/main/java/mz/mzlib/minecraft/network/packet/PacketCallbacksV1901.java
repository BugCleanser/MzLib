package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.PacketCallbacks", begin = 1901))
public interface PacketCallbacksV1901 extends WrapperObject
{
    WrapperFactory<PacketCallbacksV1901> FACTORY = WrapperFactory.of(PacketCallbacksV1901.class);
    @Deprecated
    @WrapperCreator
    static PacketCallbacksV1901 create(Object wrapped)
    {
        return WrapperObject.create(PacketCallbacksV1901.class, wrapped);
    }
}
