package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket", end=1400),
                @VersionName(name = "net.minecraft.server.network.packet.ClientSettingsC2SPacket", begin=1400, end=1502),
                @VersionName(name = "net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket", begin=1502, end=2002),
                @VersionName(name = "net.minecraft.network.packet.c2s.common.ClientOptionsC2SPacket", begin=2002)
        })
public interface PacketC2sClientSettings extends Packet
{
    default String getLanguage()
    {
        if(MinecraftPlatform.instance.getVersion()<2002)
            return this.getLanguageV_2002();
        else
            return this.getOptionsV2002().getLanguage();
    }
    default int getViewDistance()
    {
        if(MinecraftPlatform.instance.getVersion()<2002)
            return this.getViewDistanceV_2002();
        else
            return this.getOptionsV2002().getViewDistance();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="language", end=2002))
    String getLanguageV_2002();
    @WrapMinecraftFieldAccessor(@VersionName(name = "viewDistance", end=2002))
    int getViewDistanceV_2002();

    @WrapMinecraftFieldAccessor(@VersionName(name="options", begin=2002))
    SyncedClientOptionsV2002 getOptionsV2002();

    @WrapMinecraftClass(@VersionName(begin=2002,name = "net.minecraft.network.packet.c2s.common.SyncedClientOptions"))
    interface SyncedClientOptionsV2002 extends WrapperObject
    {
        @WrapMinecraftFieldAccessor(@VersionName(name="language"))
        String getLanguage();
        @WrapMinecraftFieldAccessor(@VersionName(name = "viewDistance"))
        int getViewDistance();
    }
}
