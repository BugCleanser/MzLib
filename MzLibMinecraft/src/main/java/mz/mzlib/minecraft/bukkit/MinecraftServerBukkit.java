package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.RemoteConsoleCommandSender;

@BukkitOnly
@WrapSameClass(MinecraftServer.class)
public interface MinecraftServerBukkit extends MinecraftServer
{
    @WrapperCreator
    static MinecraftServerBukkit create(Object wrapped)
    {
        return WrapperObject.create(MinecraftServerBukkit.class, wrapped);
    }
    
    @WrapFieldAccessor("remoteConsole")
    RemoteConsoleCommandSender getRemoteConsole();
}
