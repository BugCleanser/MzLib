package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.*;
import org.bukkit.command.RemoteConsoleCommandSender;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapSameClass(MinecraftServer.class)
public interface MinecraftServerBukkit extends MinecraftServer
{
    WrapperFactory<MinecraftServerBukkit> FACTORY = WrapperFactory.of(MinecraftServerBukkit.class);
    @Deprecated
    @WrapperCreator
    static MinecraftServerBukkit create(Object wrapped)
    {
        return WrapperObject.create(MinecraftServerBukkit.class, wrapped);
    }
    
    @VersionRange(end=2002)
    @WrapFieldAccessor("remoteConsole")
    RemoteConsoleCommandSender getRemoteConsoleV_2002();
}
