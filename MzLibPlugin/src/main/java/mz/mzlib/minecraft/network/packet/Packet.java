package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(name = "net.minecraft.network.Packet", end = 1904), @VersionName(name = "net.minecraft.network.packet.Packet", begin = 1904)})
public interface Packet extends WrapperObject
{
    @WrapperCreator
    static Packet create(Object wrapped)
    {
        return WrapperObject.create(Packet.class, wrapped);
    }
}
