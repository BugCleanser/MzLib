package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.command.CommandSource", end=1400), @VersionName(name="net.minecraft.server.command.CommandOutput", begin=1400)})
public interface CommandSender extends WrapperObject
{
    @WrapMinecraftMethod(@VersionName(name="sendMessage"))
    void sendMessage(Text msg);
}
