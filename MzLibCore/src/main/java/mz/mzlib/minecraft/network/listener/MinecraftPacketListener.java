package mz.mzlib.minecraft.network.listener;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.listener.PacketListener"))
public interface MinecraftPacketListener extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static MinecraftPacketListener create(Object object)
    {
        return WrapperObject.create(MinecraftPacketListener.class, object);
    }
}
