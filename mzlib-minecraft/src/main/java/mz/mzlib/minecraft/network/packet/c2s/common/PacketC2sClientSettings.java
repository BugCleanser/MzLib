package mz.mzlib.minecraft.network.packet.c2s.common;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.Impl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

/**
 * nameV_2002: net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket
 * nameV2002: net.minecraft.network.packet.c2s.common.ClientOptionsC2SPacket
 */
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket", end = 1400),
    @VersionName(name = "net.minecraft.class_2803", begin = 1400)
})
public interface PacketC2sClientSettings extends WrapperObject, Packet
{
    WrapperFactory<PacketC2sClientSettings> FACTORY = WrapperFactory.of(PacketC2sClientSettings.class);
    @VersionRange(begin = 2002)
    @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1963"))
    DataV2002 getDataV2002();

    String getLanguage();
    @Impl("getLanguage")
    @VersionRange(end = 2002)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "language", end = 1400),
        @VersionName(name = "field_12777", begin = 1400, end = 1800),
        @VersionName(name = "comp_266", begin = 1800)
    })
    String getLanguageV_2002();
    @Impl("getLanguage")
    @VersionRange(begin = 2002)
    default String getLanguageV2002()
    {
        return this.getDataV2002().getLanguage();
    }

    @VersionRange(begin = 2002)
    @WrapMinecraftClass(@VersionName(name = "net.minecraft.class_8791"))
    interface DataV2002 extends WrapperObject
    {
        WrapperFactory<DataV2002> FACTORY = WrapperFactory.of(DataV2002.class);
        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1951"))
        String getLanguage();
    }
}

