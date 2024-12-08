package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.GameObject;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.command.ServerCommandSource"))
public interface CommandSource extends WrapperObject
{
    @WrapperCreator
    static CommandSource create(Object wrapped)
    {
        return WrapperObject.create(CommandSource.class, wrapped);
    }
    
    // TODO
    @WrapMinecraftFieldAccessor(@VersionName(name="output"))
    GameObject getGameObject();
}
