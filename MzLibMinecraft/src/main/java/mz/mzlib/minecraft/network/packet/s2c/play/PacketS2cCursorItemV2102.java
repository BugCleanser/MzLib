package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 2102)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.packet.s2c.play.SetCursorItemS2CPacket"))
public interface PacketS2cCursorItemV2102 extends Packet
{
    WrapperFactory<PacketS2cCursorItemV2102> FACTORY = WrapperFactory.of(PacketS2cCursorItemV2102.class);

    @WrapMinecraftMethod(@VersionName(name = "comp_2890"))
    ItemStack getValue();

    static PacketS2cCursorItemV2102 newInstance(ItemStack value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }
    @WrapConstructor
    PacketS2cCursorItemV2102 static$newInstance(ItemStack value);
}
