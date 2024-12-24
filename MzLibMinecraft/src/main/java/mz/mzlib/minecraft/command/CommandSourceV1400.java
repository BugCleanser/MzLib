package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.command.ServerCommandSource", begin=1400))
public interface CommandSourceV1400 extends WrapperObject
{
    @WrapperCreator
    static CommandSourceV1400 create(Object wrapped)
    {
        return WrapperObject.create(CommandSourceV1400.class, wrapped);
    }
    
    // TODO
    @WrapMinecraftFieldAccessor(@VersionName(name="output"))
    CommandSender getSender();
}
