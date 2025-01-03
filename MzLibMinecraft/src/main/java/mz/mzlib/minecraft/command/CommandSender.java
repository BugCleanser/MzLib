package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.UUID;

@WrapMinecraftClass({@VersionName(name="net.minecraft.command.CommandSource", end=1400), @VersionName(name="net.minecraft.server.command.CommandOutput", begin=1400)})
public interface CommandSender extends WrapperObject
{
    void sendMessage(Text msg);
    
    @SpecificImpl("sendMessage")
    @WrapMinecraftMethod({@VersionName(name="sendMessage", end=1600), @VersionName(name="sendMessage", begin=1900)})
    void sendMessageV_1600__1900(Text msg);
    
    @WrapMinecraftMethod(@VersionName(name="sendSystemMessage", begin=1600, end=1900))
    void sendMessageV1600_1900(Text msg, UUID sender);
    
    @SpecificImpl("sendMessage")
    @VersionRange(begin=1600, end=1900)
    default void sendMessageV1600_1900(Text msg)
    {
        sendMessageV1600_1900(msg, null);
    }
}
