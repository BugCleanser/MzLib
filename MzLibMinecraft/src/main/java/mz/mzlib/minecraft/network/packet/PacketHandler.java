package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.listener.PacketListener"))
public interface PacketHandler extends WrapperObject
{
    WrapperFactory<PacketHandler> FACTORY = WrapperFactory.of(PacketHandler.class);
    @Deprecated
    @WrapperCreator
    static PacketHandler create(Object wrapped)
    {
        return WrapperObject.create(PacketHandler.class, wrapped);
    }
}
